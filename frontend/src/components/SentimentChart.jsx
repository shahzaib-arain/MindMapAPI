import { useEffect, useRef } from 'react';
import { Box, Typography } from '@mui/material';
import { Chart, registerables } from 'chart.js';
import { countBy } from 'lodash';

Chart.register(...registerables);

const SentimentChart = ({ entries }) => {
  const chartRef = useRef(null);
  const chartInstance = useRef(null);

  useEffect(() => {
    if (entries.length === 0) return;

    const sentimentCounts = countBy(entries, 'sentiment');
    const labels = Object.keys(sentimentCounts);
    const data = Object.values(sentimentCounts);

    const ctx = chartRef.current.getContext('2d');

    if (chartInstance.current) {
      chartInstance.current.destroy();
    }

    chartInstance.current = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels,
        datasets: [
          {
            data,
            backgroundColor: [
              '#4CAF50', // Happy - green
              '#F44336', // Sad - red
              '#FF9800', // Neutral - orange
              '#9C27B0', // Angry - purple
              '#2196F3', // Excited - blue
            ],
            borderWidth: 0,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'right',
          },
          tooltip: {
            callbacks: {
              label: function (context) {
                const label = context.label || '';
                const value = context.raw || 0;
                const total = context.dataset.data.reduce((a, b) => a + b, 0);
                const percentage = Math.round((value / total) * 100);
                return `${label}: ${value} (${percentage}%)`;
              },
            },
          },
        },
        cutout: '70%',
      },
    });

    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [entries]);

  return (
    <Box sx={{ height: '300px', position: 'relative' }}>
      <canvas ref={chartRef} />
    </Box>
  );
};

export default SentimentChart;