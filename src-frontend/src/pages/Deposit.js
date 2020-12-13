import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Deposit(props)  {

    const [accounts,setAccounts] = React.useState([]);

    React.useEffect(() => {

            axios.get(`https://staging.drbyron.io/v1/accounts/client-2`,
            {headers:
            {
                'Content-Type':'application/json',
                'Authorization':'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ'
            }})
            .then(res => {
                //console.log(res.data);
                setAccounts(res.data);
            })

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

        /*axios.post(`https://staging.drbyron.io/v1/account/deposit`,
        {
            headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ'
            },

        })*/

        axios.post(`https://staging.drbyron.io/v1/account/deposit`,
        {

            amount: Number(data.amt),
            account_id:data.to_acc,
            source_id:'000011112222444',

        },{headers: {
         Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ',
         }})
            .then(res => {
            console.log(res.data);
        })

        /*axios.post(`http://localhost:8080/v1/account/`,
               {
                   amount: data.start_balance
                   account_id: data.to_acc
                   source_id:
               })
                   .then(res => {
                   console.log(res.data);
                   //setNewAccount(res.data);
               })*/


    }

    return(
        <div style={{display:"flex", flexDirection:"column", alignItems:"center"}}>
            <h1 style={{marginTop:50}}>Deposit Funds</h1>
            <form onSubmit={handleSubmit(onSubmit)} style={{display:"flex",flexDirection:"column", rowGap:20, alignItems:"flex-start"}}>
                <span>
                    <label style={{marginRight:10}}>To</label>
                    <select name="to_acc" ref={register({  })} style={{marginRight:10}}>
                        {/* Will loop through accounts list with the option tag */}
                        {
                        accounts.map((account,key) =><option key={key} value={account.id} style={{wordSpacing:50}}>{account.id}</option> )
                        }
                    </select>
                </span>

                <span>    
                    <label style={{marginRight:10}}>Amount</label>
                    <label>$</label>
                    <input name="amt" type="number" step="0.01"
                        ref={register({ required: true,validate:{
                            positive: value => value >=0 || "Input must be a positive value" ,
                            nonzero: value => value>0 || "Input must be a non-zero value", 
                        }})}  
                        style={{marginRight:10}}/>
                    <p style={{color:"red"}}>{errors.amt && errors.amt.message}</p>

                </span>

                    <input type="submit" value="Confirm Deposit" style={{alignSelf:"center",marginTop:5,marginBottom:25}}/>
            </form>
            <button onClick={(e)=>props.history.push({pathname: '/dashboard'})}>Back</button>
        </div>
    )

}
