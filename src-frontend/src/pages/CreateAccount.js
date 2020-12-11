import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Dashboard(props)  {

    React.useEffect(() => {

        if(props.token===""){
            props.history.replace({pathname: '/login'});
        }

        axios.get(`http://localhost:8080/dashboard/`,{headers:{Authorization:props.token}})
        .then(res => {
            if(res.data==="You do not have access to access this page."){
                props.history.replace({pathname: '/login'});
            }
        })
    });

    const { register, errors, handleSubmit } = useForm({
        mode: "onBlur"
    });

    const onSubmit = async (data) => {
        // axios.post(`http://localhost:8080/CCMinCalculator`,
        // {
        //     CCBalance:data.ccBalance,
        //     CCInterestRate:data.ccInterest,
        //     minimumPaymentPercentage: data.minPayPercent,
        //     APIKey:312736
        // })
        //     .then(res => {
        //     console.log(res.data);
        //     setCCMinResults(res.data);
        // })


    }

    return(
        <div style={{display:"flex", flexDirection:"column", alignItems:"center"}}>
            <h1 style={{marginTop:50}}>Create Account</h1>
            <form onSubmit={handleSubmit(onSubmit)} style={{display:"flex",flexDirection:"column", rowGap:20, alignItems:"flex-start"}}>
                <span>
                    <label style={{marginRight:10}}>Description</label>
                    <input name="description" type="text" ref={register({})} style={{marginRight:10}}/>
                </span>

                <span>    
                    <label style={{marginRight:10}}>Account Type</label>
                    <select name="acc_type" ref={register({  })} style={{marginRight:10}}>
                        <option value="checking">Checking</option>
                        <option value="savings">Savings</option>
                        <option value="money-market">Money-market</option>
                        <option value="credit-card">Credit-card</option>
                        <option value="loan">Loan</option>
                        <option value="liability">Liability</option>
                    </select>
                </span>

                <span>    
                    <label style={{marginRight:10}}>Starting Balance</label>
                    <label>$</label>
                    <input name="start_balance" type="number" step="0.01" 
                        ref={register({ required: true,validate:{
                            positive: value => value >=0 || "Input must be a positive value" ,
                            nonzero: value => value>0 || "Input must be a non-zero value", 
                        }})}  
                        style={{marginRight:10}}/>
                    <p style={{color:"red"}}>{errors.start_balance && errors.start_balance.message}</p>
                </span>

                    <input type="submit" value="Create New Account" style={{alignSelf:"center",marginTop:5,marginBottom:25}}/>
            </form>
            <button onClick={(e)=>props.history.push({pathname: '/dashboard'})}>Back</button>

        </div>
    )

}