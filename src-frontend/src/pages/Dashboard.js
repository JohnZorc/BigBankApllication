import React from 'react';

export default class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            num_accounts: 4,
            is_admin:false
        };
      }

    render() {
        return(

            <div>

                
                {this.state.is_admin ? 

                    <div style={{display:"flex", flexDirection:"column",alignItems:"flex-start",marginLeft:30}}>
                        <h1>BAMS Transactions LOGS</h1>
                    </div>
                
                :

                    <div style={{display:"flex", flexDirection:"column"}}>
                        <div className="header" style={{display:"flex", flexDirection:"row", justifyContent:"flex-end", alignItems:"center",marginRight:30}}>
                            <h3>Username</h3>
                            <button style={{marginLeft:25,maxHeight:25}}>Log Out</button>
                        </div>

                        <div className="row_container" style={{display:"flex", flexDirection:"row"}}>
                            <div className="accounts" style={{display:"flex", flex:1, flexDirection:"column", alignItems:"center", marginLeft:30}}>
                                <h1 style={{marginLeft:-40}}>Accounts <span> <button>Deposit</button> </span> </h1>
                                <div style={{display:"grid",gridTemplateColumns:"50% 50%",gridTemplateRows:"5% (this.state.num_accounts+1)",marginBottom:25}}>
                                    
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
                                <button style={{marginBottom:30}}>Create New Account</button>
                                <button>Transfer Funds</button>
                                
                            </div>

                            <div className="cfi-functions" style={{display:"flex",flex:2, flexDirection:"column", alignItems:"flex-start"}}>
                                <h1 style={{alignSelf:"center"}}>CFI Functions</h1>
                                
                                <div className="cc-min">
                                    <h3>Credit Card Minimum Payment Calculator</h3>
                                </div>

                                <div className="mort-calc">
                                    <h3>Mortgage Calculator</h3>
                                </div>

                                <div className="cc-payoff">
                                    <h3>Credit Card Payoff Calculator</h3>
                                    {/* <form onSubmit={this.ccPayoffSubmit}>
                                        <label>
                                            Credit Card Balance:
                                            <input type="number" value={this.state.value} onChange={this.handleChange} />
                                        </label>
                                        <button type="submit">
                                            Calculate
                                        </button>
                                    </form> */}
                                </div>

                                <div className="simp-savings" style={{}}>
                                    <h3>Simple Savings Calculator</h3>
                                </div>


                            </div>
                        </div>
                    </div>
                }
                
            </div>
        )
    }

}