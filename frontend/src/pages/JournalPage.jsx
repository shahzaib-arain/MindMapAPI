import { useState, useEffect } from 'react';
import { Box, Button, Typography, CircularProgress, Grid, Card, CardContent, CardActions } from '@mui/material';
import { Add as AddIcon } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import { journalService } from '../services/api';
import { motion } from 'framer-motion';
import SentimentDisplay from '../components/SentimentDisplay';

const JournalPage = () => {
  const [entries, setEntries] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchEntries = async () => {
      try {
        const response = await journalService.getEntries();
        setEntries(response.data);
      } catch (error) {
        console.error('Error fetching entries:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchEntries();
  }, []);

  const handleDelete = async (id) => {
    try {
      await journalService.deleteEntry(id);
      setEntries(entries.filter(entry => entry.id !== id));
    } catch (error) {
      console.error('Error deleting entry:', error);
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="200px">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" fontWeight="bold">
          My Journal Entries
        </Typography>
        <Button
          variant="contained"
          startIcon={<AddIcon />}
          onClick={() => navigate('/journal/new')}
        >
          New Entry
        </Button>
      </Box>

      {entries.length === 0 ? (
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          justifyContent="center"
          minHeight="300px"
          textAlign="center"
        >
          <Typography variant="h6" color="textSecondary" gutterBottom>
            No journal entries yet
          </Typography>
          <Typography variant="body1" color="textSecondary" mb={3}>
            Start by creating your first journal entry
          </Typography>
          <Button
            variant="contained"
            startIcon={<AddIcon />}
            onClick={() => navigate('/journal/new')}
          >
            Create Entry
          </Button>
        </Box>
      ) : (
        <Grid container spacing={3}>
          {entries.map((entry) => (
            <Grid item xs={12} sm={6} md={4} key={entry.id}>
              <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.3 }}
              >
                <Card
                  sx={{
                    height: '100%',
                    display: 'flex',
                    flexDirection: 'column',
                    '&:hover': {
                      boxShadow: '0px 6px 20px rgba(0, 0, 0, 0.1)',
                      transform: 'translateY(-2px)',
                      transition: 'all 0.3s ease',
                    },
                  }}
                >
                  <CardContent sx={{ flexGrow: 1 }}>
                    <Typography variant="h6" gutterBottom>
                      {entry.title}
                    </Typography>
                    <Typography
                      variant="body2"
                      color="text.secondary"
                      sx={{
                        display: '-webkit-box',
                        WebkitLineClamp: 3,
                        WebkitBoxOrient: 'vertical',
                        overflow: 'hidden',
                        mb: 2,
                      }}
                    >
                      {entry.content}
                    </Typography>
                    <Box display="flex" alignItems="center" mt={2}>
                      <Typography variant="caption" color="text.secondary">
                        {new Date(entry.date).toLocaleDateString()}
                      </Typography>
                      {entry.sentiment && (
                        <Box ml="auto">
                          <SentimentDisplay sentiment={entry.sentiment} />
                        </Box>
                      )}
                    </Box>
                  </CardContent>
                  <CardActions sx={{ justifyContent: 'flex-end' }}>
                    <Button
                      size="small"
                      onClick={() => navigate(`/journal/${entry.id}`)}
                    >
                      View
                    </Button>
                    <Button
                      size="small"
                      color="error"
                      onClick={() => handleDelete(entry.id)}
                    >
                      Delete
                    </Button>
                  </CardActions>
                </Card>
              </motion.div>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
};

export default JournalPage;