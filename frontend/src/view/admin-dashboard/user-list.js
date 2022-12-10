import React from "react";
import {Col, Row} from "antd";
import UserCard from "./user-card";
import {Empty} from "antd";

const UserList = ({userData, action}) =>
  userData && userData.length > 0 ? (
    <Row gutter={[20, 20]} style={{width: "100%", margin: "0"}}>
      {userData.map((val) => (
        <Col xs={24} lg={12} xl={6} key={val.id + action}>
          <UserCard action={action} userData={val} />
        </Col>
      ))}
    </Row>
  ) : (
    <Empty />
  );

export default UserList;
