import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Dashboard(props)  {

    React.useEffect(() => {

        axios.get(`http://localhost:8080/dashboard/`,{headers:{Authorization:props.token}})
            .then(res => {
                if(res.data==="You do not have access to access this page."){
                    props.history.replace({pathname: '/login'});
                }
            })
    });

    const [ccMinResults,setCCMinResults] = React.useState("");
    const [mortCalcResults,setmortCalcResults] = React.useState("");
    const [ccPayResults,setccPayResults] = React.useState("");
    const [simpSavResults,setsimpSavResults] = React.useState(""); 
    const is_admin = false;
    
    const { register, errors, handleSubmit } = useForm({
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

    return(

        <div>

            {
            is_admin ? 

                <div style={{display:"flex", flexDirection:"column",alignItems:"flex-start",marginLeft:30, marginBottom:70}}>
                    <div className="header" style={{display:"flex", flexDirection:"row", justifyContent:"flex-end", alignItems:"center",marginRight:30}}>
                        <h3>Welcome back, {props.customer.firstName}</h3>
                        <button style={{marginLeft:25,maxHeight:25}} onClick={(e)=>props.history.push({pathname: '/login'})}>Log Out</button>
                    </div>
                    <h1>BAMS Transactions LOGS</h1>
                    {/* Loop through the logs */}
                    {/* <h4>
                        
                    </h4> */}
                </div>
            
            :

                <div style={{display:"flex", flexDirection:"column", marginBottom:70}}>
                    <div className="header" style={{display:"flex", flexDirection:"row", justifyContent:"flex-end", alignItems:"center",marginRight:30}}>
                        <h3>Welcome back, {props.customer.firstName}</h3>
                        <button style={{marginLeft:25,maxHeight:25}} onClick={(e)=>props.history.push({pathname: '/login'})}>Log Out</button>
                    </div>

                    <div className="row_container" style={{display:"flex", flexDirection:"row"}}>
                        <div className="accounts" style={{display:"flex", flex:1, flexDirection:"column", alignItems:"center", marginLeft:30}}>
                            <h1 style={{marginLeft:-40}}>Accounts 
                                <span> 
                                    <button style={{marginLeft:20}}onClick={(e)=>props.history.push({pathname: '/deposit',token:props.location.token})}>Deposit</button> 
                                </span> 
                            </h1>
                            {/* We will use this grid div in a loop within a column flex div */}
                            <div style={{display:"grid",gridTemplateColumns:"50% 50%",gridTemplateRows:"auto",marginBottom:25}}>
                                
                                <p>Account #2342</p>
                                <p>$1000.00</p>

                                <p>Account #4542</p>
                                <p>$1000.00</p>

                                <p>Account #2342</p>
                                <p>$1000.00</p>

                                <p>Account #2342</p>
                                <p>$1000.00</p>

                                <p>Net Worth/Total Balance</p>
                                <p>$1000.00</p>

                            </div>
                            <button style={{marginBottom:30}} onClick={(e)=>props.history.push({pathname: '/new_account',token:props.location.token})}>Create New Account</button>
                            <button onClick={(e)=>props.history.push({pathname: '/transfer',token:props.location.token})}>Transfer Funds</button>
                            
                        </div>

                        <div className="cfi-functions" style={{display:"flex",flex:2, flexDirection:"column", alignItems:"center"}}>
                            <h1>CFI Functions</h1>
                            
                            <div className="cc-min" style={{display:"flex",flexDirection:"column"}}>
                                <h3>Credit Card Minimum Payment Calculator</h3>
                                <form onSubmit={handleSubmit(onCCMinSubmit)} style={{display:"flex",flexDirection:"column", rowGap:15, alignItems:"flex-start"}}>
                                    <span>
                                        <label htmlFor="ccBalance" style={{marginRight:10}}>Credit Card Balance</label>
                                        <input name="ccBalance" type="number" step="0.01" ref={register({ required: true })} style={{marginRight:10}}/>
                                        {/* validate: value => value !== "admin" || "Nice try!" */}
                                        {/* {errors.firstName && errors.firstName.message} */}
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Credit Card Interest Rate (enter as decimal)</label>
                                        <input name="ccInterest" type="number" step="0.01" ref={register} style={{marginRight:10}}/>
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Minimum Payment Percentage (enter as decimal)</label>
                                        <input name="minPayPercent" type="number" step="0.01" ref={register} style={{marginRight:10}}/>
                                        {/* validate: value => value <5 || "too high!" */}
                                        {/* {errors.age && errors.age.message} */}
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
                                <form onSubmit={handleSubmit2(onMortSubmit)} style={{display:"flex",flexDirection:"column", rowGap:15, alignItems:"flex-start"}}>
                                    <span>
                                        <label style={{marginRight:10}}>Home Price</label>
                                        <input name="homePrice" type="number" step="0.01" ref={register2({required: true})} style={{marginRight:10}}/>
                                        {/* {errors.firstName && errors.firstName.message} */}
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Down Payment Percentage (enter as decimal)</label>
                                        <input name="downPayPercent" type="number" step="0.01" ref={register2({  })} style={{marginRight:10}}/>
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Length of Loan (in whole years)</label>
                                        <input name="years" type="number" ref={register2({})} style={{marginRight:10}}/>
                                        {/* {errors.age && errors.age.message} */}
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Interest Rate (enter as decimal)</label>
                                        <input name="interest" type="number" step="0.01" ref={register2({ })} style={{marginRight:10}}/>
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
                                <form onSubmit={handleSubmit3(onCCPaySubmit)} style={{display:"flex",flexDirection:"column", rowGap:15, alignItems:"flex-start"}}>
                                    <span>
                                        <label style={{marginRight:10}}>Credit Card Balance</label>
                                        <input name="ccBalance" type="number" step="0.01" ref={register3({})} style={{marginRight:10}}/>
                                        {/* validate: value => value !== "admin" || "Nice try!" */}
                                        {/* {errors.firstName && errors.firstName.message} */}
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Credit Card Interest Rate (enter as decimal)</label>
                                        <input name="ccInterest" type="number" step="0.01" ref={register3({  })} style={{marginRight:10}}/>
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Desired Months to Pay Off</label>
                                        <input name="months" type="number" ref={register3({})} style={{marginRight:10}}/>
                                        {/* {errors.age && errors.age.message} */}
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
                                <form onSubmit={handleSubmit4(onSimpSavSubmit)} style={{display:"flex",flexDirection:"column", rowGap:15, alignItems:"flex-start"}}>
                                    <span>
                                        <label style={{marginRight:10}}>Initial Deposit</label>
                                        <input name="initDeposit" type="number" step="0.01" ref={register4({})} style={{marginRight:10}}/>
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Monthly Contribution</label>
                                        <input name="monthlyContri" type="number" step="0.01" ref={register4({  })} style={{marginRight:10}}/>
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Period (in years)</label>
                                        <input name="period" type="number" step="0.01" ref={register4({})} style={{marginRight:10}}/>
                                        {/* {errors.age && errors.age.message} */}
                                    </span>

                                    <span>    
                                        <label style={{marginRight:10}}>Simple Yearly Interest Rate (APY)</label>
                                        <input name="apy" type="number" step="0.01" ref={register4({})} style={{marginRight:10}}/>
                                        {/* {errors.age && errors.age.message} */}
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