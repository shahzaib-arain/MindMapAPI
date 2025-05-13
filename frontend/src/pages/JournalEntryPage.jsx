import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
  Box,
  Typography,
  CircularProgress,
  Button,
  Paper,
  Chip,
} from '@mui/material';
import { Edit as EditIcon, Delete as DeleteIcon } from '@mui/icons-material';
import { journalService } from '../services/api';
import SentimentDisplay from '../components/SentimentDisplay';
import { motion } from 'framer-motion';

const JournalEntryPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [entry, setEntry] = useState(null);
  const [loading, setLoading] = useState(true);
  const [deleting, setDeleting] = useState(false);

  useEffect(() => {
    const fetchEntry = async () => {
      try {
        const response = await journalService.getEntryById(id);
        setEntry(response.data);
      } catch (error) {
        console.error('Error fetching entry:', error);
        navigate('/journal');
      } finally {
        setLoading(false);
      }
    };

    fetchEntry();
  }, [id, navigate]);

  const handleDelete = async () => {
    if (window.confirm('Are you sure you want to delete this entry?')) {
      setDeleting(true);
      try {
        await journalService.deleteEntry(id);
        navigate('/journal');
      } catch (error) {
        console.error('Error deleting entry:', error);
      } finally {
        setDeleting(false);
      }
    }
  };

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="200px">
        <CircularProgress />
      </Box>
    );
  }

  if (!entry) {
    return (
      <Box>
        <Typography variant="h6">Entry not found</Typography>
      </Box>
    );
  }

  return (
    <Box
      component={motion.div}
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.3 }}
    >
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h4" fontWeight="bold">
          {entry.title}
        </Typography>
        <Box display="flex" gap={2}>
          <Button
            variant="contained"
            startIcon={<EditIcon />}
            onClick={() => navigate(`/journal/${id}/edit`)}
          >
            Edit
          </Button>
          <Button
            variant="outlined"
            color="error"
            startIcon={<DeleteIcon />}
            onClick={handleDelete}
            disabled={deleting}
          >
            {deleting ? <CircularProgress size={24} /> : 'Delete'}
          </Button>
        </Box>
      </Box>

      <Paper elevation={0} sx={{ p: 3, mb: 3, borderRadius: 2 }}>
        <Box display="flex" alignItems="center" mb={3} gap={2}>
          <Chip
            label={new Date(entry.date).toLocaleDateString('en-US', {
              year: 'numeric',
              month: 'long',
              day: 'numeric',
              weekday: 'long',
            })}
            variant="outlined"
          />
          {entry.sentiment && <SentimentDisplay sentiment={entry.sentiment} />}
        </Box>

        <Typography variant="body1" whiteSpace="pre-line">
          {entry.content}
        </Typography>
      </Paper>
    </Box>
  );
};

export default JournalEntryPage;