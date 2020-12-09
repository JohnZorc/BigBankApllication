import React from 'react';
import {Link} from "react-router-dom";
import {Container, Form} from 'semantic-ui-react'
import { Button, Card } from 'semantic-ui-react'
import axios from 'axios'
import Redirect from "react-router-dom/es/Redirect";
import styles from "../styles";
import '../main.css'

function Login() {

        const [form, setForm] = React.useState({
               email: '',
               password: ''
            });
            const [registrationStatus, setRegistrationStatus] = React.useState(false);

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
                    e.preventDefault();
                    axios.post(`http://localhost:8080/login`, form)
                        .then(() => {
                            alert("User was successfully created");
                            setRegistrationStatus(true);
                        })
                        .catch(() => {
                            alert("An error occurred while logging in!");
                            setRegistrationStatus(false);
                        })
                };

                if(registrationStatus){
                        return (<Redirect to={'/Dashboard'}/>)
                    }

        return(
            <Container style ={styles.container}>
                        <Card style={styles.card}>
                            <Card.Content style={styles.content}>
                                <Card.Header>
                                    <h1 style={styles.heading}>Login</h1>
                                </Card.Header>
                                <Card.Description>
                                    <Form>
                                        <Form.Field>
                                            <label>E-mail Address</label>
                                            <input required name={'email'} placeholder='E-mail Address' onChange={handleChange} value={form.email}/>
                                        </Form.Field>
                                        <Form.Field>
                                            <label>Password</label>
                                            <input name={'password'} type={'password'} placeholder='Password' onChange={handleChange} value={form.password}/>
                                        </Form.Field>
                                    </Form>
                                </Card.Description>

                                <div style={styles.buttons}>
                                    <Link to={'/register'}>
                                        <Button color={'red'} style={styles.button}>Register</Button>
                                    </Link>
                                        <Button color={'blue'} style={styles.button} onClick={handleSubmit}>Submit</Button>
                                </div>
                            </Card.Content>
                        </Card>
                    </Container>
        );

}
export default Login;
