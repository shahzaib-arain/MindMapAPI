import { Box, Typography, Paper } from '@mui/material';
import { useEffect, useState } from 'react';
import { userService } from '../services/api';

const GreetingBanner = () => {
  const [greeting, setGreeting] = useState('');

  useEffect(() => {
    const fetchGreeting = async () => {
      try {
        const response = await userService.getGreeting();
        setGreeting(response.data);
      } catch (error) {
        console.error('Error fetching greeting:', error);
      }
    };

    fetchGreeting();
  }, []);

  return (
    <Paper
      elevation={0}
      sx={{
        p: 3,
        mb: 3,
        borderRadius: 2,
        background: 'linear-gradient(135deg, #6C63FF 0%, #A5A2FF 100%)',
        color: 'white',
      }}
    >
      <Typography variant="h5" fontWeight="bold" gutterBottom>
        {greeting || 'Welcome to Your Journal'}
      </Typography>
      <Typography variant="body1">
        {new Date().toLocaleDateString('en-US', {
          weekday: 'long',
          year: 'numeric',
          month: 'long',
          day: 'numeric',
        })}
      </Typography>
    </Paper>
  );
};

export default GreetingBanner;