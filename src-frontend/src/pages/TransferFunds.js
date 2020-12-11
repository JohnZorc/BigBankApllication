import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Dashboard(props)  {

    const { register, errors, handleSubmit } = useForm({
        mode: "onBlur"
    });

    const onSubmit = async (data) => {



         axios.post(`http://localhost:8080/CCMinCalculator`,
         {
             from_account: data.from_acc,
             to_account: data.to_acc,
             amount: data.start_balance
         })
             .then(res => {
             console.log(data);
         })


    }

    return(
        <div style={{display:"flex", flexDirection:"column", alignItems:"center"}}>
            <h1 style={{marginTop:50}}>Transfer Funds</h1>
            <form onSubmit={handleSubmit(onSubmit)} style={{display:"flex",flexDirection:"column", rowGap:20, alignItems:"flex-start"}}>
                <span>
                    <label style={{marginRight:10}}>From</label>
                    <select name="from_acc" ref={register({  })} style={{marginRight:40}}>
                        {/* Will loop through accounts list with the option tag */}
                        <option value="Acc#">Acc#</option>
                    </select>

                    <label style={{marginRight:10}}>To</label>
                    <select name="to_acc" ref={register({  })} style={{marginRight:10}}>
                        {/* Will loop through accounts list with the option tag */}
                        <option value="Acc#">Acc#</option>
                    </select>
                </span>

                <span>    
                    <label style={{marginRight:10}}>Amount</label>
                    <label>$</label>
                    <input name="start_balance" type="number" step="0.01" ref={register({})} style={{marginRight:10}}/>
                    {/* {errors.age && errors.age.message} */}
                </span>

                    <input type="submit" value="Confirm Transfer" style={{alignSelf:"center",marginTop:20,marginBottom:25}}/>
            </form>
            {/* <button onClick={(e)=>props.history.push({pathname: '/dashboard',token:props.location.token})}>Back</button> */}

        </div>
    )

}