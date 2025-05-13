import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { AuthProvider } from "./context/AuthContext"; // Your context provider
import { BrowserRouter } from "react-router-dom";  // Import BrowserRouter
import reportWebVitals from "./reportWebVitals"; // Optional for logging web vitals

// Wrap the entire app with BrowserRouter here in index.js
ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <BrowserRouter> {/* Wrap your entire app in BrowserRouter */}
      <AuthProvider> {/* Your AuthProvider context */}
        <App /> {/* Your main app component */}
      </AuthProvider>
    </BrowserRouter>
  </React.StrictMode>
);

// Log web vitals to console (optional)
reportWebVitals(console.log);
