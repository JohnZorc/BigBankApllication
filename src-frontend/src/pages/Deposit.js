import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Deposit(props)  {

    const [accounts,setAccounts] = React.useState([]);

    React.useEffect(() => {
        
        if(props.token===""){
            props.history.replace({pathname: '/login'});
        }

        axios.get(`http://localhost:8080/dashboard/`,{headers:{Authorization:props.token}})
        .then(res => {
            if(res.data==="You do not have access to access this page."){
                props.history.replace({pathname: '/login'});
            }else{
                axios.get(`https://accounts.drbyron.io/v1/accounts/${props.customer.customerID}`,
                {headers:
                {
                    'Content-Type':'application/json',
                    'Authorization':'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhY2NvdW50cy5kcmJ5cm9uLmlvIiwiZXhwIjoxNjA5ODg3MTA1LCJwcm9mIjoiRHIuIEJ5cm9uIiwidGVhbSI6InRlYW0tOSJ9.sTGbBBhTTGubq9DxYEDaarLNymvZPU03bXfZ2aEJm1Q'
                }})
                .then(res => {
                    //console.log(res.data);
                    setAccounts(res.data);
                })
            }
        })
        
    });

    const { register, errors, handleSubmit } = useForm({
        mode: "onBlur"
    });

    const onSubmit = async (data) => {

        axios.post(`https://accounts.drbyron.io/v1/account/deposit`,
        {

            amount: Number(data.amt),
            account_id:data.to_acc,
            source_id:'000011112222444',

        },{headers: {
         Authorization: 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhY2NvdW50cy5kcmJ5cm9uLmlvIiwiZXhwIjoxNjA5ODg3MTA1LCJwcm9mIjoiRHIuIEJ5cm9uIiwidGVhbSI6InRlYW0tOSJ9.sTGbBBhTTGubq9DxYEDaarLNymvZPU03bXfZ2aEJm1Q',
         }})
            .then(res => {
            console.log(res.data);
            axios.post(`http://localhost:8080/createLog`,
            {
                transactionType:"deposit",
                customerID: props.customer.customerID,
                account1:data.to_acc,
                account2:"",
                amount:Number(data.amt),
                newBalance: Number(res.data.new_balance)

            })
            props.history.push({pathname: '/dashboard'});


        })


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
