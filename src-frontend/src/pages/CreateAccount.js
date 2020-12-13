import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function CreateAccount(props)  {

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

        axios.post(`https://staging.drbyron.io/v1/account`,
       {
        
           client_id:'client-2',//props.customer.customerID,
           type:data.acc_type,
           balance:Number(data.start_balance),
           
       },{headers: {
        Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ',
        }})
           .then(res => {
           console.log(res.data);
       })


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
