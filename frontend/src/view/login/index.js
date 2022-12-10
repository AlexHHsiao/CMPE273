import React, {useState} from "react";
import {Card, Col, Row} from "antd";
import {cardTab} from "../../utils/constants/login";
import LoginForm from "./login-form";
import RegisterForm from "./register-form";

const contentList = {
  login: <LoginForm />,
  register: <RegisterForm />,
};

const Login = () => {
  const [selection, setSelection] = useState("login");

  const selectionHandler = (key) => {
    setSelection(key);
  };

  return (
    <Row justify="center" align="middle" style={{height: "100%", padding: "30px"}}>
      <Col xs={24} md={14}>
        <Card
          tabList={cardTab}
          tabBarExtraContent="Welcome!"
          activeTabKey={selection}
          onTabChange={(key) => selectionHandler(key)}
        >
          {contentList[selection]}
        </Card>
      </Col>
    </Row>
  );
};

export default Login;
