import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate, useParams } from 'react-router-dom';
import { journalService, sentimentService } from '../services/api';
import {
  Box,
  Button,
  TextField,
  Typography,
  CircularProgress,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Grid,
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import { motion } from 'framer-motion';
import dayjs from 'dayjs'; // Add this import

const JournalEntryForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [sentiments, setSentiments] = useState([]);
  const [isEditMode, setIsEditMode] = useState(false);
  const {
    register,
    handleSubmit,
    setValue,
    watch,
    control, // Add control to use with DatePicker
    formState: { errors },
  } = useForm({
    defaultValues: {
      title: '',
      content: '',
      sentiment: '',
      date: dayjs(), // Initialize with current date
    }
  });

  useEffect(() => {
    const fetchSentiments = async () => {
      try {
        const response = await sentimentService.getSentiments();
        setSentiments(response.data);
      } catch (error) {
        console.error('Error fetching sentiments:', error);
      }
    };

    fetchSentiments();

    if (id && id !== 'new') {
      setIsEditMode(true);
      const fetchEntry = async () => {
        setLoading(true);
        try {
          const response = await journalService.getEntryById(id);
          const entry = response.data;
          setValue('title', entry.title);
          setValue('content', entry.content);
          setValue('sentiment', entry.sentiment);
          setValue('date', dayjs(entry.date)); // Use dayjs for date
        } catch (error) {
          console.error('Error fetching entry:', error);
          navigate('/journal');
        } finally {
          setLoading(false);
        }
      };
      fetchEntry();
    }
  }, [id, navigate, setValue]);

 const onSubmit = async (data) => {
   setLoading(true);
   try {
     const payload = {
       title: data.title,
       content: data.content,
       sentiment: data.sentiment || 'neutral',
       date: dayjs(data.date).toISOString() // Proper date formatting
     };

     console.log('Submitting:', payload);

     const response = isEditMode
       ? await journalService.updateEntry(id, payload)
       : await journalService.createEntry(payload);

     console.log('Response:', response.data);
     navigate('/journal');
   } catch (error) {
     console.error('Full error:', error);
     alert(`Error: ${error.response?.data?.message || error.message}`);
   } finally {
     setLoading(false);
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
    <Box
      component={motion.div}
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.3 }}
    >
      <Typography variant="h4" fontWeight="bold" mb={4}>
        {isEditMode ? 'Edit Journal Entry' : 'Create New Journal Entry'}
      </Typography>
      <Box
        component="form"
        onSubmit={handleSubmit(onSubmit)}
        sx={{ maxWidth: '800px' }}
      >
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Title"
              variant="outlined"
              {...register('title', { required: 'Title is required' })}
              error={!!errors.title}
              helperText={errors.title?.message}
            />
          </Grid>
          <Grid item xs={12} md={6}>
            <DatePicker
              label="Date"
              value={watch('date')}
              onChange={(newValue) => setValue('date', newValue)}
              slotProps={{
                textField: {
                  fullWidth: true,
                },
              }}
            />
          </Grid>
          <Grid item xs={12} md={6}>
            <FormControl fullWidth>
              <InputLabel>Sentiment</InputLabel>
              <Select
                label="Sentiment"
                {...register('sentiment')}
                defaultValue=""
              >
                {sentiments.map((sentiment) => (
                  <MenuItem key={sentiment} value={sentiment}>
                    {sentiment}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              label="Content"
              variant="outlined"
              multiline
              rows={8}
              {...register('content', { required: 'Content is required' })}
              error={!!errors.content}
              helperText={errors.content?.message}
            />
          </Grid>
          <Grid item xs={12}>
            <Box display="flex" justifyContent="flex-end" gap={2}>
              <Button
                variant="outlined"
                onClick={() => navigate('/journal')}
              >
                Cancel
              </Button>
              <Button
                type="submit"
                variant="contained"
                disabled={loading}
              >
                {loading ? (
                  <CircularProgress size={24} />
                ) : isEditMode ? (
                  'Update Entry'
                ) : (
                  'Create Entry'
                )}
              </Button>
            </Box>
          </Grid>
        </Grid>
      </Box>
    </Box>
  );
};

export default JournalEntryForm;