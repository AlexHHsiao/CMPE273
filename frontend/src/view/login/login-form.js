import React from "react";
import {Button, Form, Input} from "antd";
import {validateMessages} from "../../utils/constants/form";
import {useDispatch} from "react-redux";

const LoginForm = () => {
  const dispatch = useDispatch();

  const onFinish = (values) => {
    dispatch({
      type: "user/login",
      payload: values,
    });
  };

  return (
    <Form
      layout="vertical"
      name="login"
      onFinish={onFinish}
      validateMessages={validateMessages}
    >
      <Form.Item
        label="Username"
        name="username"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Password"
        name="password"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <Input.Password />
      </Form.Item>

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Login
        </Button>
      </Form.Item>
    </Form>
  );
};

export default LoginForm;
