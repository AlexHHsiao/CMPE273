import React from "react";
import {Row, Col} from "antd";
import InfoDashboard from "./info-dashboard";
import TradeDashboard from "./trade-dashboard";

const Profile = () => {
  return (
    <>
      <Row gutter={[40, 30]} style={{width: "100%", height: "100%", margin: "0"}}>
        <Col xs={24} lg={8}>
          <InfoDashboard />
        </Col>
        <Col xs={24} lg={16}>
          <TradeDashboard />
        </Col>
      </Row>
    </>
  );
};

export default Profile;
