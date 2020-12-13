import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Dashboard(props)  {

    const [logs,setLogs] = React.useState([]);
    const [accounts,setAccounts] = React.useState([]);
    const [networth,setNetworth] = React.useState(0);

    React.useEffect(() => {

//         if(localStorage.getItem('customer')==="admin"){
//             axios.get(`http://localhost:8080/GetAllLogs/`)
//                 .then(res => {
//                     setLogs(res.data);
//             })
//         }else{

//             if(props.token===""){
//                 props.history.replace({pathname: '/login'});
//             }else{
//                 axios.get(`http://localhost:8080/dashboard/`,{headers:{Authorization:props.token}})
//                 .then(res => {
//                     if(res.data==="You do not have access to access this page."){
//                         localStorage.setItem('token',""); 
//                         props.setToken(""); 
//                         localStorage.setItem('customer',"");
//                         props.history.replace({pathname: '/login'});
//                         alert("You've been logged out from an expired token.");
//                     }
//             })
//             }

            axios.get(`https://staging.drbyron.io/v1/accounts/client-1`,
            {headers:
            {
                'Content-Type':'application/json',
                'Authorization':'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ'
            }})
            .then(res => {
                console.log(res.data);
                accounts.push(res.data);
            })


            var nWorth;
            var i;
            for(i=0; i<accounts.length; i++)
            {
                nWorth = nWorth + accounts[i].balance;
            }
            setNetworth(nWorth);

            //add axios func for getting all associated accounts here
            //perform func to get networth after
//         }
    });






    const [ccMinResults,setCCMinResults] = React.useState("");
    const [mortCalcResults,setmortCalcResults] = React.useState("");
    const [ccPayResults,setccPayResults] = React.useState("");
    const [simpSavResults,setsimpSavResults] = React.useState("");
    
    const { register, errors, handleSubmit, getValues,clearErrors } = useForm({
        mode: "onBlur"
      });
    
      const {
        register: register2,
        errors: errors2,
        handleSubmit: handleSubmit2
      } = useForm({
        mode: "onBlur"
      });

      const {
        register: register3,
        errors: errors3,
        handleSubmit: handleSubmit3
      } = useForm({
        mode: "onBlur"
      });

      const {
        register: register4,
        errors: errors4,
        handleSubmit: handleSubmit4
      } = useForm({
        mode: "onBlur"
      });

    const onCCMinSubmit = async (data) => {
        axios.post(`http://localhost:8080/CCMinCalculator/`,
        {
            CCBalance:data.ccBalance,
            CCInterestRate:data.ccInterest,
            minimumPaymentPercentage: data.minPayPercent,
            APIKey:props.customer.APIKey
        })
        .then(res => {
            console.log(res.data);
            setCCMinResults(res.data);
        })
    }

    const onMortSubmit = async (data) => {
        axios.post(`http://localhost:8080/MortgageCalculator/`, 
        {   
            homePrice:data.homePrice,
            downPaymentAsPercent:data.downPayPercent,
            loanLength: data.years,
            interestRate: data.interest,
            APIKey:props.customer.APIKey
        })
            .then(res => {
            console.log(res.data);
            setmortCalcResults(res.data);
        })
        
    }

    const onCCPaySubmit = async (data) => {
        axios.post(`http://localhost:8080/CCPayoffCalculator/`, 
        {   
            CCBalance:data.ccBalance,
            CCInterest:data.ccInterest,
            Months: data.months,
            APIKey:props.customer.APIKey
        })
        .then(res => {
            console.log(res.data);
            setccPayResults(res.data);
        })
        
    }

    const onSimpSavSubmit = async (data) => {
        axios.post(`http://localhost:8080/SimpleSavings/`, 
        {   
            deposit:data.initDeposit,
            monthly:data.monthlyContri,
            yearPeriods: data.period,
            interestRate: data.apy,
            APIKey:props.customer.APIKey
        })
            .then(res => {
            console.log(res.data);
            setsimpSavResults(res.data);
        })
        
    }

    /*const onNewAccountSubmit = async (data) => {
                axios.post(`http://staging.drbyron.io/v1/account/`,
                {
                    client_id:props.customer.customerID,
                    type:data.acc_type,
                    balance:data.start_balance
                })
                    .then(res => {
                    console.log(res.data);
                    setNewAccount(res.data);
                })
            }*/

    return(

        <div>

            {
            localStorage.getItem('customer')==="admin" ? 

                <div style={{display:"flex", flexDirection:"column",alignItems:"flex-start",marginLeft:30, marginBottom:70}}>
                    <div className="header" style={{display:"flex", flexDirection:"row", justifyContent:"flex-end", alignItems:"center",alignSelf:"flex-end",marginRight:30}}>
                        <button style={{marginLeft:25,marginTop:25,maxHeight:25}} onClick={(e)=>{localStorage.setItem('token',""); props.setToken(""); localStorage.setItem('customer',""); props.history.push({pathname: '/login'});}}>Log Out</button>
                    </div>
                    <h1>BAMS Transactions LOGS</h1>
                    {/*
                    Loop through the logs


                    */}
                    {
                        logs.map((log,key) => <h4 key={key} style={{marginBottom:-15}}>{log}</h4>)
                    }
                </div>
            
            :

                <div style={{display:"flex", flexDirection:"column", marginBottom:70}}>
                    <div className="header" style={{display:"flex", flexDirection:"row", justifyContent:"flex-end", alignItems:"center",marginRight:30}}>
                        <h3>Welcome back, {props.customer.firstName}</h3>
                        <button style={{marginLeft:25,maxHeight:25}} onClick={(e)=>{localStorage.setItem('token',""); props.setToken(""); localStorage.setItem('customer',""); props.history.push({pathname: '/login'});}}>Log Out</button>
                    </div>

                    <div className="row_container" style={{display:"flex", flexDirection:"row"}}>
                        <div className="accounts" style={{display:"flex", flex:1, flexDirection:"column", alignItems:"center", marginLeft:30}}>
                            <h1 style={{marginLeft:-40}}>Accounts 
                                <span> 
                                    <button style={{marginLeft:20}}onClick={(e)=>props.history.push({pathname: '/deposit',token:props.location.token})}>Deposit</button> 
                                </span> 
                            </h1>

                            <div style={{display:"flex", flexDirection:"column",alignItems:"center",rowGap:10,marginBottom:25}}>


                                    {
                                    accounts.map((account,key) =><p key={key} style={{wordSpacing:50}}> <strong>{account.id}</strong> {account.balance}</p> )
                                    }


                                <p style={{wordSpacing:50}}><strong>Net Worth/Total Balance</strong> {networth}</p>

                            </div>
                            <button style={{marginBottom:30}} onClick={(e)=>props.history.push({pathname: '/new_account',token:props.location.token})}>Create New Account</button>
                            <button onClick={(e)=>props.history.push({pathname: '/transfer',token:props.location.token})}>Transfer Funds</button>
                            
                        </div>

                        <div className="cfi-functions" style={{display:"flex",flex:2, flexDirection:"column", alignItems:"center"}}>
                            <h1>CFI Functions</h1>
                            
                            <div className="cc-min" style={{display:"flex",flexDirection:"column"}}>
                                <h3>Credit Card Minimum Payment Calculator</h3>
                                <form onSubmit={handleSubmit(errors.minPayPercent?null:onCCMinSubmit)} style={{display:"flex",flexDirection:"column", alignItems:"flex-start"}}>
                                    
                                    {/* only positive vals and nonzero*/}
                                    <span>
                                        <label htmlFor="ccBalance" style={{marginRight:10}}>Credit Card Balance</label>
                                        <input name="ccBalance" type="number" step="0.01" 
                                            ref={register({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value",
                                                tooLow: value => (getValues("minPayPercent")===""||getValues("minPayPercent") > ( value*(getValues("ccInterest")/100) )) || "Minimum payment percentage is too low",
                                                }})}  
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors.ccBalance && errors.ccBalance.message}</p>
                                    </span>
                                    

                                    {/* only positive vals and nonzero*/}
                                    <span>    
                                        <label style={{marginRight:10}}>Credit Card Interest Rate (enter as decimal)</label>
                                        <input name="ccInterest" type="number" step="0.01" 
                                            ref={register({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value",
                                                tooLow: value => (getValues("minPayPercent")===""||getValues("minPayPercent")> ( getValues("ccBalance")*(value/100) )) || "Minimum payment percentage is too low",
                                                }})} 
                                            style={{marginRight:10}}
                                        />
                                        <p style={{color:"red"}}>{errors.ccInterest && errors.ccInterest.message}</p>
                                    </span>


                                    {/* only positive vals and nonzero*/}
                                    {/* has to be more than ccbalance*(ccinterest/100) */}
                                    <span>    
                                        <label style={{marginRight:10}}>Minimum Payment Percentage (enter as decimal)</label>
                                        <input name="minPayPercent" type="number" step="0.01" 
                                            ref={register({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value",
                                                tooLow: value => value> ( getValues("ccBalance")*(getValues("ccInterest")/100) ) || "Minimum payment percentage is too low",
                                                }})} 
                                            onChange = {()=>{clearErrors('ccBalance');clearErrors('ccInterest')}}
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors.minPayPercent && errors.minPayPercent.message}</p>
                                    </span>

                                        <input type="submit" value="Calculate" style={{maxWidth:70,alignSelf:"center",marginBottom:25}}/>
                                </form>

                                {ccMinResults!=="" ? <div><strong>Calculations</strong> 
                                                        <p><strong>Estimated Monthly Payment: </strong>${ccMinResults.monthlyPayment}</p> 
                                                        <p><strong>Estimated Months Needed: </strong>{ccMinResults.months}</p> 
                                                        <p><strong>Estimated Total Amount Paid: </strong>${ccMinResults.totalAmountPaid}</p>
                                                    </div> 
                                : null}

                            </div>

                            <div className="mort-calc" style={{display:"flex",flexDirection:"column", marginTop:35}}>
                                <h3>Mortgage Calculator</h3>
                                <form onSubmit={handleSubmit2(onMortSubmit)} style={{display:"flex",flexDirection:"column", alignItems:"flex-start"}}>
                                    
                                    {/* must be more than zero, but not zero */}
                                    <span>
                                        <label style={{marginRight:10}}>Home Price</label>
                                        <input name="homePrice" type="number" step="0.01" 
                                            ref={register2({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                                }})}  
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors2.homePrice && errors2.homePrice.message}</p>
                                    </span>

                                    {/* must be more than zero, but not zero */}
                                    <span>    
                                        <label style={{marginRight:10}}>Down Payment Percentage (enter as decimal)</label>
                                        <input name="downPayPercent" type="number" step="0.01" 
                                            ref={register2({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                                }})}  
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors2.downPayPercent && errors2.downPayPercent.message}</p>

                                    </span>

                                    {/* must be more than zero, but not zero */}
                                    {/* cannot be decimal */}
                                    <span>    
                                        <label style={{marginRight:10}}>Length of Loan (in whole years)</label>
                                        <input name="years" type="number" 
                                            ref={register2({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                                decimal: value => (value% 1===0) || "Input must be a whole number"
                                                }})}  
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors2.years && errors2.years.message}</p>
                                    </span>

                                    {/* must be more than zero, but not zero */}
                                    <span>    
                                        <label style={{marginRight:10}}>Interest Rate (enter as decimal)</label>
                                        <input name="interest" type="number" step="0.01" 
                                            ref={register2({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                                }})}  
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors2.interest && errors2.interest.message}</p>

                                    </span>

                                        <input type="submit" value="Calculate" style={{maxWidth:70,alignSelf:"center",marginBottom:25}}/>
                                </form>

                                {mortCalcResults!=="" ? <div><strong>Calculations</strong> 
                                                        <p><strong>Estimated Monthly Payment: </strong>{mortCalcResults.monthlyPayment}</p> 
                                                        <p><strong>Estimated Paid in Principle: </strong>{mortCalcResults.amountPainInPrinciple}</p> 
                                                        <p><strong>Estimated Paid in Interest: </strong>{mortCalcResults.amountPaidInInterest}</p>
                                                        <p><strong>Estimated Total Amount Paid: </strong>{mortCalcResults.totalAmountPaid}</p>
                                                    </div> 
                                : null}

                            </div>

                            <div className="cc-payoff" style={{display:"flex",flexDirection:"column", marginTop:35}}>
                                <h3>Credit Card Payoff Calculator</h3>
                                <form onSubmit={handleSubmit3(onCCPaySubmit)} style={{display:"flex",flexDirection:"column", alignItems:"flex-start"}}>
                                    <span>
                                        {/* must be more than zero, but not zero */}
                                        <label style={{marginRight:10}}>Credit Card Balance</label>
                                        <input name="ccBalance" type="number" step="0.01" 
                                            ref={register3({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                                }})} 
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors3.ccBalance && errors3.ccBalance.message}</p>

                                    </span>

                                    <span>    
                                        {/* must be postive */}
                                        <label style={{marginRight:10}}>Credit Card Interest Rate (enter as decimal)</label>
                                        <input name="ccInterest" type="number" step="0.01" 
                                            ref={register3({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                }})} 
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors3.ccInterest && errors3.ccInterest.message}</p>

                                    </span>

                                    <span>    
                                        {/* must be more than zero, but not zero */}
                                        <label style={{marginRight:10}}>Desired Months to Pay Off</label>
                                        <input name="months" type="number" 
                                            ref={register3({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                            }})}
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors3.months && errors3.months.message}</p>
                                    </span>

                                        <input type="submit" value="Calculate" style={{maxWidth:70,alignSelf:"center",marginBottom:25}}/>
                                </form>

                                {ccPayResults!=="" ? <div><strong>Calculations</strong> 
                                                        <p><strong>Estimated Monthly Payment: </strong>{ccPayResults.MonthlyPayment}</p> 
                                                        <p><strong>Estimated Total Interest Paid: </strong>{ccPayResults.CCTotalInterest}</p> 
                                                        <p><strong>Estimated Total Balance Paid: </strong>{ccPayResults.CCTotalBalance}</p>
                                                    </div> 
                                : null}

                            </div>

                            <div className="simp-savings" style={{display:"flex",flexDirection:"column", marginTop:35}}>
                                <h3>Simple Savings Calculator</h3>
                                <form onSubmit={handleSubmit4(onSimpSavSubmit)} style={{display:"flex",flexDirection:"column", alignItems:"flex-start"}}>
                                    <span>
                                        <label style={{marginRight:10}}>Initial Deposit</label>
                                        <input name="initDeposit" type="number" step="0.01" 
                                            ref={register4({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                            }})}
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors4.initDeposit && errors4.initDeposit.message}</p>
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Monthly Contribution</label>
                                        <input name="monthlyContri" type="number" step="0.01" 
                                            ref={register4({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                            }})}
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors4.monthlyContri && errors4.monthlyContri.message}</p>

                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Period (in years)</label>
                                        <input name="period" type="number" step="0.01"
                                            ref={register4({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                            }})}
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors4.period && errors4.period.message}</p>                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Simple Yearly Interest Rate (APY)</label>
                                        <input name="apy" type="number" step="0.01" 
                                            ref={register4({ required: true,validate:{
                                                positive: value => value >=0 || "Input must be a positive value" ,
                                                nonzero: value => value>0 || "Input must be a non-zero value", 
                                            }})}
                                            style={{marginRight:10}}/>
                                        <p style={{color:"red"}}>{errors4.apy && errors4.apy.message}</p>
                                    </span>

                                        <input type="submit" value="Calculate" style={{maxWidth:70,alignSelf:"center",marginBottom:25}}/>
                                </form>

                                {simpSavResults!=="" ? <div><strong>Calculations</strong> 
                                                        <p><strong>Estimated Total Savings: </strong>${simpSavResults.TotalSavings}</p> 
                                                        <p><strong>Estimated Total Interest Earned: </strong>${simpSavResults.TotalInterestEarned}</p> 
                                                        <p><strong>Estimated Total Contributions: </strong>${simpSavResults.TotalContributions}</p>
                                                    </div> 
                                : null}

                            </div>


                        </div>
                    </div>
                </div>
            }
            
        </div>
    )
    

}
