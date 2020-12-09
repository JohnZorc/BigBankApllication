import React from 'react';
import { useForm } from "react-hook-form";
import axios from 'axios';

export default function Dashboard(props)  {

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
        <div>
            <h3>Create Account</h3>
            <form onSubmit={handleSubmit(onSubmit)} style={{display:"flex",flexDirection:"column", rowGap:15, alignItems:"flex-start"}}>
                <span>
                    <label style={{marginRight:10}}>Initial Deposit</label>
                    <input name="initDeposit" type="number" step="0.01" ref={register({})} style={{marginRight:10}}/>
                </span>

                <span>    
                    <label style={{marginRight:10}}>Monthly Contribution</label>
                    <input name="monthlyContri" type="number" step="0.01" ref={register({  })} style={{marginRight:10}}/>
                </span>

                <span>    
                    <label style={{marginRight:10}}>Period (in years)</label>
                    <input name="period" type="number" step="0.01" ref={register({})} style={{marginRight:10}}/>
                    {/* {errors.age && errors.age.message} */}
                </span>

                <span>    
                    <label style={{marginRight:10}}>Simple Yearly Interest Rate (APY)</label>
                    <input name="apy" type="number" step="0.01" ref={register({})} style={{marginRight:10}}/>
                    {/* {errors.age && errors.age.message} */}
                </span>

                    <input type="submit" value="Calculate" style={{maxWidth:70,alignSelf:"center",marginBottom:25}}/>
            </form>
        </div>
    )

}