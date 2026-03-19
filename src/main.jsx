import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { AuthProvider } from './config/AuthProvider.jsx'
import { BrowserRouter } from 'react-router-dom'
import { GoogleOAuthProvider } from '@react-oauth/google';



createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
    <AuthProvider>
    <GoogleOAuthProvider clientId="733444828187-buner8kh2t0cil6p6r5epklmuhl0heqn.apps.googleusercontent.com">
       <App />
    </GoogleOAuthProvider>
    </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
)
