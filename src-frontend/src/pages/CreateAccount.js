import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Dashboard(props)  {

    const { register, errors, handleSubmit } = useForm({
        mode: "onBlur"
    });

    const onSubmit = async (data) => {

       axios.post(`http://staging.drbyron.io/v1/account/`,
       {
           headers: {
           'Access-Control-Allow-Origin': '*',
           'Origin': '*',
           'Accept': 'application/json',
           'Content-Type': 'application/json',
           'Authorization': props.token
           },
           client_id:props.customer.customerID,
           type:data.acc_type,
           balance:data.start_balance
       })
           .then(res => {
           console.log(data);
           axios.post(`http://localhost:8080/addAccount`,
           {
               client_id:props.customer.customerID,
               type:data.acc_type.value,
               balance:data.start_balance.value
           })
       })


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
                    <input name="start_balance" type="number" step="0.01" ref={register({})} style={{marginRight:10}}/>
                    {/* {errors.age && errors.age.message} */}
                </span>

                    <input type="submit" value="Create New Account" style={{alignSelf:"center",marginTop:20,marginBottom:25}}/>
            </form>
            {/* <button onClick={(e)=>props.history.push({pathname: '/dashboard',token:props.location.token})}>Back</button> */}

        </div>
    )

}