import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Grid, Box, Button, TextField, Typography, CircularProgress, Avatar, Paper } from '@mui/material';
import { userService } from '../services/api';
import SentimentChart from '../components/SentimentChart';
import { motion } from 'framer-motion';
import { useAuth } from "../context/AuthContext";

const ProfilePage = () => {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [updating, setUpdating] = useState(false);
  const { register, handleSubmit, setValue, formState: { errors } } = useForm();
  const { user, logout } = useAuth();

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await userService.getUserProfile();
        setUserData(response.data);
        setValue('userName', response.data.userName);
        setValue('email', response.data.email);
      } catch (error) {
        console.error('Error fetching user data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [setValue]);

  const onSubmit = async (data) => {
    setUpdating(true);
    try {
      await userService.updateUserProfile(data);
      const response = await userService.getUserProfile();
      setUserData(response.data);
    } catch (error) {
      console.error('Error updating profile:', error);
    } finally {
      setUpdating(false);
    }
  };

  const handleDeleteAccount = async () => {
    if (window.confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
      try {
        await userService.deleteAccount();
        logout();
      } catch (error) {
        console.error('Error deleting account:', error);
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

  return (
    <Box
      component={motion.div}
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      transition={{ duration: 0.3 }}
    >
      <Typography variant="h4" fontWeight="bold" mb={4}>
        My Profile
      </Typography>
      <Grid container spacing={4}>
        <Grid item xs={12} md={6}>
          <Paper elevation={0} sx={{ p: 3, borderRadius: 2 }}>
            <Box display="flex" flexDirection="column" alignItems="center" mb={4}>
              <Avatar
                sx={{
                  width: 100,
                  height: 100,
                  fontSize: 40,
                  bgcolor: 'primary.main',
                  mb: 2,
                }}
              >
                {userData?.userName?.charAt(0).toUpperCase()}
              </Avatar>
              <Typography variant="h6">{userData?.userName}</Typography>
              <Typography variant="body2" color="text.secondary">
                {userData?.email}
              </Typography>
            </Box>
            <Box
              component="form"
              onSubmit={handleSubmit(onSubmit)}
            >
              <TextField
                fullWidth
                label="Username"
                variant="outlined"
                margin="normal"
                {...register('userName', { required: 'Username is required' })}
                error={!!errors.userName}
                helperText={errors.userName?.message}
              />
              <TextField
                fullWidth
                label="Email"
                variant="outlined"
                margin="normal"
                type="email"
                {...register('email', {
                  required: 'Email is required',
                  pattern: {
                    value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
                    message: 'Invalid email address',
                  },
                })}
                error={!!errors.email}
                helperText={errors.email?.message}
              />
              <Box display="flex" justifyContent="flex-end" mt={3} gap={2}>
                <Button
                  type="submit"
                  variant="contained"
                  disabled={updating}
                >
                  {updating ? <CircularProgress size={24} /> : 'Update Profile'}
                </Button>
              </Box>
            </Box>
          </Paper>
        </Grid>
        <Grid item xs={12} md={6}>
          <Paper elevation={0} sx={{ p: 3, borderRadius: 2, height: '100%' }}>
            <Typography variant="h6" gutterBottom>
              Sentiment Analysis
            </Typography>
            {userData?.journalEntities?.length > 0 ? (
              <SentimentChart entries={userData.journalEntities} />
            ) : (
              <Typography variant="body2" color="text.secondary">
                No journal entries available for sentiment analysis.
              </Typography>
            )}
          </Paper>
        </Grid>
        <Grid item xs={12}>
          <Paper elevation={0} sx={{ p: 3, borderRadius: 2 }}>
            <Typography variant="h6" gutterBottom color="error">
              Danger Zone
            </Typography>
            <Button
              variant="outlined"
              color="error"
              onClick={handleDeleteAccount}
            >
              Delete Account
            </Button>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
};

export default ProfilePage;