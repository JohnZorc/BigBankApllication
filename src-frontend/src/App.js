import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Register from './pages/Register';
import Deposit from './pages/Deposit';
import CreateAcc from './pages/CreateAccount';
import Transfer from './pages/TransferFunds';

function App() {
  return (
    <div className="App" >
      <BrowserRouter>
        <Switch>
          <Route exact path="/dashboard" render={(props)=> <Dashboard {...props}/>}/>
          <Route exact path="/login" render={(props)=> <Login {...props}/>}/>
          <Route exact path="/register" render={(props)=> <Register {...props}/>}/>
          <Route exact path="/deposit" render={(props)=> <Deposit {...props}/>}/>
          <Route exact path="/new_account" render={(props)=> <CreateAcc {...props}/>}/>
          <Route exact path="/transfer" render={(props)=> <Transfer {...props}/>}/>
          <Route exact path="/">
              {/* <Redirect to="/login" />  */}
              {/* <Redirect to="/deposit" /> */}
              <Redirect to="/dashboard" />

          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
