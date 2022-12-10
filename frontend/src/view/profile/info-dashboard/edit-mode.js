import React, {useEffect} from "react";
import {Button, Form, Input, DatePicker} from "antd";
import moment from "moment";
import {dateFormat, validateMessages} from "../../../utils/constants/form";

const EditMode = ({onFinish, userInfo}) => {
  const [form] = Form.useForm();

  useEffect(() => {
    const values = {};
    userInfo.forEach(({name, value}) => {
      values[name] = name === "birthday" ? moment(value, dateFormat) : value;
    });
    form.setFieldsValue(values);
  }, [userInfo]);

  return (
    <Form
      form={form}
      layout="vertical"
      name="edit-user-info"
      onFinish={onFinish}
      validateMessages={validateMessages}
    >
      {userInfo.map(({label, editable, name}) => (
        <Form.Item
          label={label}
          name={name}
          rules={[
            {
              required: true,
              ...(label === "Email" ? {type: "email"} : {}),
            },
          ]}
          key={name}
        >
          {name === "birthday" ? <DatePicker /> : <Input disabled={!editable} />}
        </Form.Item>
      ))}

      <Form.Item>
        <Button type="primary" htmlType="submit">
          Save
        </Button>
      </Form.Item>
    </Form>
  );
};

export default EditMode;
