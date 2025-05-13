import { ThemeProvider } from '@mui/material/styles';
import { CssBaseline } from '@mui/material';
import { Routes, Route, Navigate } from 'react-router-dom';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { theme } from './styles/theme';
import { AuthProvider, useAuth } from './context/AuthContext';
import Layout from './components/Layout';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import JournalPage from './pages/JournalPage';
import JournalEntryPage from './pages/JournalEntryPage';
import JournalEntryForm from './components/JournalEntryForm';
import ProfilePage from './pages/ProfilePage';
import AdminPage from './pages/AdminPage';
import NotFoundPage from './pages/NotFoundPage';

const ProtectedRoute = ({ children, adminOnly = false }) => {
  const { user, loading } = useAuth();

  if (loading) return <div>Loading...</div>;

  if (!user) return <Navigate to="/login" replace />;

  if (adminOnly && !user.roles?.includes('ADMIN')) {
    return <Navigate to="/journal" replace />;
  }

  return children;
};

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <AuthProvider>
          <Layout>
            <Routes>
              <Route path="/login" element={<LoginPage />} />
              <Route path="/signup" element={<SignupPage />} />
              <Route
                path="/journal"
                element={
                  <ProtectedRoute>
                    <JournalPage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/journal/new"
                element={
                  <ProtectedRoute>
                    <JournalEntryForm />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/journal/:id"
                element={
                  <ProtectedRoute>
                    <JournalEntryPage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/journal/:id/edit"
                element={
                  <ProtectedRoute>
                    <JournalEntryForm />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/profile"
                element={
                  <ProtectedRoute>
                    <ProfilePage />
                  </ProtectedRoute>
                }
              />
              <Route
                path="/admin"
                element={
                  <ProtectedRoute adminOnly>
                    <AdminPage />
                  </ProtectedRoute>
                }
              />
              <Route path="/" element={<Navigate to="/journal" replace />} />
              <Route path="*" element={<NotFoundPage />} />
            </Routes>
          </Layout>
        </AuthProvider>
      </LocalizationProvider>
    </ThemeProvider>
  );
}

export default App;