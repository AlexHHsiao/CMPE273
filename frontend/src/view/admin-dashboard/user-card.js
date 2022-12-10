import React from "react";
import {Card, Typography, Popconfirm, Descriptions, Popover} from "antd";
import {DeleteOutlined, UserAddOutlined, UserDeleteOutlined} from "@ant-design/icons";
import {
  pendingTab,
  removeUserConfirmMessage,
} from "../../utils/constants/admin-dashboard";
import {useDispatch} from "react-redux";

const UserCard = ({action, userData}) => {
  const dispatch = useDispatch();
  const {Title, Paragraph} = Typography;
  const {username, creditScore, email, legalName, phone, role, id} = userData;

  const approveUserHandler = () => {
    dispatch({
      type: "admin/updateUser",
      payload: id,
    });
  };

  const removeUserHandler = () => {
    dispatch({
      type: "admin/deleteUser",
      payload: id,
    });
  };

  const cardActionHandler = () =>
    action === pendingTab
      ? [
          <Popover content="Approve">
            <UserAddOutlined onClick={approveUserHandler} />
          </Popover>,
          <Popover content="Reject">
            <UserDeleteOutlined onClick={removeUserHandler} />
          </Popover>,
        ]
      : [
          <Popconfirm
            title={removeUserConfirmMessage}
            onConfirm={removeUserHandler}
            okText="Yes"
            cancelText="No"
          >
            <DeleteOutlined />
          </Popconfirm>,
        ];

  return (
    <Card actions={cardActionHandler()}>
      <Paragraph>
        <Title level={5}>{legalName}</Title>
        <Descriptions size="small" column={1}>
          <Descriptions.Item label="Username">{username}</Descriptions.Item>
          <Descriptions.Item label="Email">{email}</Descriptions.Item>
          <Descriptions.Item label="Role">{role}</Descriptions.Item>
          <Descriptions.Item label="Credit Score">{creditScore}</Descriptions.Item>
          <Descriptions.Item label="Phone">{phone}</Descriptions.Item>
        </Descriptions>
      </Paragraph>
    </Card>
  );
};

export default UserCard;
