import React from 'react';
import './App.css';
import { BrowserRouter, Switch, Route, Redirect } from 'react-router-dom';
import Dashboard from './pages/Dashboard';
import Login from './pages/Login';
import Register from './pages/Register';
import Deposit from './pages/Deposit';
import CreateAcc from './pages/CreateAccount';
import Transfer from './pages/TransferFunds';
import { detectOverflow } from '@popperjs/core';

function App() {

  const [jwtToken,setJWTToken] = React.useState(localStorage.getItem('token')||'');
  const [customer,setCustomer] = React.useState(localStorage.getItem('token')?JSON.parse(localStorage.getItem('customer')):{});
  
  return (
    <div className="App">
      <BrowserRouter>
        <Switch>
          <Route exact path="/dashboard" render={(props)=> <Dashboard {...props} token={jwtToken} customer={customer} setToken={setJWTToken}/>}/>
          <Route exact path="/login" render={(props)=> <Login {...props} token={jwtToken} setToken={setJWTToken} customer={customer} setCustomer={setCustomer}/>}/>
          <Route exact path="/register" render={(props)=> <Register {...props} token={jwtToken} setToken={setJWTToken} customer={customer} setCustomer={setCustomer}/>}/>
          <Route exact path="/deposit" render={(props)=> <Deposit {...props} token={jwtToken} customer={customer}/>}/>
          <Route exact path="/new_account" render={(props)=> <CreateAcc {...props} token={jwtToken} customer={customer}/>}/>
          <Route exact path="/transfer" render={(props)=> <Transfer {...props} token={jwtToken} customer={customer}/>}/>
          <Route exact path="/">
//               <Redirect to="/login" />
              <Redirect to="/dashboard" />
          </Route>
        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;
