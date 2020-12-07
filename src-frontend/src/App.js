import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Register from './pages/Register';

function App() {
  return (
    <div className="App" >
      <BrowserRouter>
        <Switch>
          <Route exact path="/dashboard" render={(props)=> <Dashboard {...props}/>}/>
          <Route exact path="/login" render={(props)=> <Login {...props}/>}/>
          <Route exact path="/register" render={(props)=> <Register {...props}/>}/>
          <Route exact path="/">
              <Redirect to="/login" /> 
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
