import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function TransferFunds(props)  {

    const [accounts,setAccounts] = React.useState([]);

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

            axios.get(`https://staging.drbyron.io/v1/accounts/${props.customer.customerID}`,
            {headers:
            {
                'Content-Type':'application/json',
                'Authorization':'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ'
            }})
            .then(res => {
                //console.log(res.data);
                setAccounts(res.data);
            })
    });

    const { register, errors, handleSubmit } = useForm({
        mode: "onBlur"
    });

    const onSubmit = async (data) => {
         console.log(data.from_acc);
         axios.post(`https://staging.drbyron.io/v1/account/transfer`,
         {

             from_account:data.from_acc,
             to_account:data.to_acc,
             amount:Number(data.amt),

         },{headers: {
          Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ',
          }})
             .then(res => {
             axios.post(`https://localhost:8080/createLog/`,
             {
                 transactionType:"transfer",
                 customerID:props.customer.customerID,
                 account1:data.from_acc,
                 account2:data.to_acc,
                 dollarAmount:Number(data.amt)

             })
         })

         /*axios.post(`https://staging.drbyron.io/v1/account/transfer`,
         {
             headers: {
             'Accept': 'application/json',
             'Content-Type': 'application/json',
             'Authorization': 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJzdGFnaW5nLmRyYnlyb24uaW8iLCJleHAiOjE2MDkzOTA3OTIsInByb2YiOiJEci4gQnlyb24iLCJ0ZWFtIjoidGVhbS05In0.fjSJFcPKrzrXnNH89Wn_vvcI5GiRLoghzeYsk9OUHGQ'
             },
             from_account: data.from_acc,
             to_account: data.to_acc,
             amount:data.amt

         })*/


    }

    return(
        <div style={{display:"flex", flexDirection:"column", alignItems:"center"}}>
            <h1 style={{marginTop:50}}>Transfer Funds</h1>
            <form onSubmit={handleSubmit(onSubmit)} style={{display:"flex",flexDirection:"column", rowGap:20, alignItems:"flex-start"}}>
                <span>
                    <label style={{marginRight:10}}>From</label>
                    <select name="from_acc" ref={register({  })} style={{marginRight:50}}>
                        {/* Will loop through accounts list with the option tag */}
                        {/*<option value="{accounts.id}">{accounts.id}</option>*/}
                        {
                        accounts.map((account,key) =><option key={key} value={account.id} style={{wordSpacing:50}}>{account.id}</option> )
                        }
                    </select>

                    <label style={{marginRight:10}}>To</label>
                    <select name="to_acc" ref={register({  })} style={{marginRight:50}}>
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

                    <input type="submit" value="Confirm Transfer" style={{alignSelf:"center",marginTop:5,marginBottom:25}}/>
            </form>
            <button onClick={(e)=>props.history.push({pathname: '/dashboard'})}>Back</button>

        </div>
    )

}
