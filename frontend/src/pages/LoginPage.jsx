import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
  Box,
  Button,
  TextField,
  Typography,
  CircularProgress,
  Paper,
  Grid,
} from '@mui/material';
import { motion } from 'framer-motion';
import { fadeIn, slideUp } from '../utils/animations';

const LoginPage = () => {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    setLoading(true);
    setError('');
    try {
      await login(data);
      navigate('/journal');
    } catch (err) {
      setError('Invalid username or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Grid
      container
      justifyContent="center"
      alignItems="center"
      sx={{ minHeight: '100vh' }}
      component={motion.div}
      initial="initial"
      animate="animate"
      variants={fadeIn}
    >
      <Grid item xs={12} sm={8} md={6} lg={4}>
        <Paper
          component={motion.div}
          variants={slideUp}
          elevation={3}
          sx={{
            p: 4,
            borderRadius: 2,
            background: 'linear-gradient(145deg, #ffffff, #f5f5f5)'
          }}
        >
          <Box
            textAlign="center"
            mb={4}
            component={motion.div}
            variants={slideUp}
          >
            <Typography
              variant="h4"
              fontWeight="bold"
              color="primary"
              gutterBottom
            >
              Welcome Back
            </Typography>
            <Typography
              variant="body1"
              color="text.secondary"
            >
              Log in to your journal
            </Typography>
          </Box>

          {error && (
            <Box
              bgcolor="error.light"
              color="error.contrastText"
              p={2}
              mb={2}
              borderRadius={1}
              component={motion.div}
              initial={{ opacity: 0, y: -20 }}
              animate={{ opacity: 1, y: 0 }}
              exit={{ opacity: 0 }}
            >
              <Typography>{error}</Typography>
            </Box>
          )}

          <Box
            component="form"
            onSubmit={handleSubmit(onSubmit)}
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              fullWidth
              label="Username"
              autoFocus
              {...register('userName', { required: 'Username is required' })}
              error={!!errors.userName}
              helperText={errors.userName?.message}
              sx={{ mb: 2 }}
              component={motion.div}
              variants={slideUp}
            />
            <TextField
              margin="normal"
              fullWidth
              label="Password"
              type="password"
              {...register('password', { required: 'Password is required' })}
              error={!!errors.password}
              helperText={errors.password?.message}
              sx={{ mb: 3 }}
              component={motion.div}
              variants={slideUp}
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{
                mt: 3,
                mb: 2,
                py: 1.5,
                borderRadius: 2,
                fontWeight: 'bold',
                fontSize: '1rem'
              }}
              disabled={loading}
              component={motion.button}
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
            >
              {loading ? (
                <CircularProgress size={24} color="inherit" />
              ) : (
                'Log In'
              )}
            </Button>
            <Box
              textAlign="center"
              mt={2}
              component={motion.div}
              variants={slideUp}
            >
              <Typography variant="body2" color="text.secondary">
                Don't have an account?{' '}
                <Link
                  to="/signup"
                  style={{
                    color: '#6C63FF',
                    textDecoration: 'none',
                    fontWeight: 'bold'
                  }}
                >
                  Sign up
                </Link>
              </Typography>
            </Box>
          </Box>
        </Paper>
      </Grid>
    </Grid>
  );
};

export default LoginPage;