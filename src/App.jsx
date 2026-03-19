import './App.css'
import { LoginPage } from './pages/LoginPage/LoginPage'
import { SignUpPage } from './pages/SignUpPage/SignUpPage'
import { Route, Routes } from 'react-router-dom'
import { HomePage } from './pages/HomePage/HomePage'
import { EventPage } from './pages/EventPage/EventPage'
import { Subscription } from './pages/SubscriptionPage/Subscription'
import { EventDetails } from './pages/EventDetailsPage/EventDetails'
import { CreateEventPage } from './pages/CreateEventPage/CreateEventPage'
import { ProfilePage } from './pages/ProfilePage/ProfilePage'
import OAuthSuccess from './components/OAuth/OAuth'

function App() {

  return (
    <>
    <Routes>
      <Route path='/' element={<HomePage/>} />
      <Route path='/login' element={<LoginPage/>} />
      <Route path='/signup' element={<SignUpPage/>} />
      <Route path='/events' element={<EventPage/>} />
      <Route path='/subscriptions' element={<Subscription/>} />
      <Route path='/events/:id' element={<EventDetails/>} />
      <Route path='/createEvent' element={<CreateEventPage/>} />
      <Route path='/profile' element={<ProfilePage/>} />
      <Route path="/oauth-success" element={<OAuthSuccess />} />
    </Routes>
    </>
  )
}

export default App
