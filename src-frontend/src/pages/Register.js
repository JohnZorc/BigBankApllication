import React from 'react';
import {Link} from "react-router-dom";
import {Container, Form} from 'semantic-ui-react'
import { Button, Card } from 'semantic-ui-react'
import axios from 'axios'

function Register(props) {

    // States
    const [form, setForm] = React.useState({
       firstName: '',
       lastName: '',
       emailAddress: '',
       password: '',
       password2: '',
       homeAddress:''
    });

    const handleChange = (e, data) => {
        let name, value;
        if(data){
            name = data.name;
            value = data.value;
        } else {
            name = e.target.name;
            value = e.target.value;
        }

        setForm({...form, [name]: value});
    };
    const handleSubmit = (e) => {
    if (form.password === form.password2 && form.password.length>=8)
        {
        e.preventDefault();
        axios.post(`http://localhost:8080/register`,form)
            .then(res => {
                if(res.data.token===""){
                    alert(res.data.message);
                }else{
                    // props.setToken(res.data.token);
                    props.setToken(res.data);
                    // props.setCustomer({APIKey:res.data.APIKey,customerID:res.data.customerID,firstName:res.data.firstName});
                    props.setCustomer({firstName:res.data.firstName,APIKey:34534523});                    
                    props.history.push({pathname: '/dashboard'});
                }
            })
            .catch(() => {
                alert("An error occurred while registering!");
            })
        }
        else if (form.password.length >=8)
        {
            alert("The passwords do not match!");
        }
        else
        {
            alert("The password is too short! Please use at least 8 characters!");
        }
    };


    return (
        <Container>
            <Card>
                <Card.Content>
                    <Card.Header>
                        <h1>Register</h1>
                    </Card.Header>
                    <Card.Description >
                        <Form>
                            <Form.Field>
                                <label>First Name</label>
                                <input required name={'firstName'} placeholder='First Name' onChange={handleChange} value={form.firstName}/>
                            </Form.Field>
                            <Form.Field>
                                <label>Last Name</label>
                                <input required name={'lastName'} placeholder='Last Name' onChange={handleChange} value={form.lastName}/>
                            </Form.Field>
                            <Form.Field>
                                <label>Email</label>
                                <input required name={'emailAddress'} type={'emailAddress'} placeholder='Enter E-mail Address' onChange={handleChange} value={form.email}/>
                            </Form.Field>
                            <Form.Field>
                                <label>Password</label>
                                <input name={'password'} type={'password'} placeholder='Password' onChange={handleChange} value={form.password}/>
                            </Form.Field>
                            <Form.Field>
                                <label>Confirm Password</label>
                                <input name={'password2'} type={'password'} placeholder='Re-Type Password' onChange={handleChange} value={form.password2}/>
                            </Form.Field>

                            <Form.Field>
                                <label>Address</label>
                                <input name={'homeAddress'} type={'homeAddress'} placeholder='Address' onChange={handleChange} value={form.address}/>
                            </Form.Field>
                        </Form>
                    </Card.Description>

                    <div style={{marginTop:20}}>
                        <Link to={'/'}>
                            <Button color={'red'}>Back</Button>
                        </Link>
                            <Button color={'blue'} onClick={handleSubmit}>Sign Up</Button>
                    </div>
                </Card.Content>
            </Card>
        </Container>
    );

}

export default Register;