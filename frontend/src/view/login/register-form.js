import React from "react";
import {Form, Input, Button, Select, DatePicker, InputNumber} from "antd";
import {dateFormat, validateMessages} from "../../utils/constants/form";
import {useDispatch} from "react-redux";
import {userRole, realtorRole} from "../../utils/constants/API";

const RegisterForm = () => {
  const {Option} = Select;
  const dispatch = useDispatch();

  const onFinish = (values) => {
    values.birthday = values.birthday.format(dateFormat);

    dispatch({
      type: "user/register",
      payload: values,
    });
  };

  return (
    <Form
      layout="vertical"
      name="register"
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
        label="Email"
        name="email"
        rules={[
          {
            required: true,
            type: "email",
          },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="Legal Name"
        name="legalName"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        name="role"
        label="Role"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <Select placeholder="Select a user type">
          <Option value={realtorRole}>Realtor</Option>
          <Option value={userRole}>User</Option>
        </Select>
      </Form.Item>
      <Form.Item
        label="Birthday"
        name="birthday"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <DatePicker />
      </Form.Item>
      <Form.Item
        label="Credit Score"
        name="creditScore"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <InputNumber />
      </Form.Item>
      <Form.Item
        label="Phone"
        name="phone"
        rules={[
          {
            required: true,
          },
        ]}
      >
        <Input />
      </Form.Item>
      <Form.Item
        label="SSN"
        name="ssn"
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
          Register
        </Button>
      </Form.Item>
    </Form>
  );
};

export default RegisterForm;
