import { Box, Toolbar, Container } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import Navbar from './Navbar';
import Sidebar from './Sidebar';
import GreetingBanner from './GreetingBanner';

const Layout = ({ children }) => {
  const { user } = useAuth();

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh' }}>
      <Navbar />
      {user && <Sidebar />}
      <Box
        component="main"
        sx={{
          flexGrow: 1,
          p: 3,
          width: { sm: `calc(100% - ${user ? 240 : 0}px)` },
          backgroundColor: 'background.default',
        }}
      >
        <Toolbar />
        {user && <GreetingBanner />}
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
          {children}
        </Container>
      </Box>
    </Box>
  );
};

export default Layout;