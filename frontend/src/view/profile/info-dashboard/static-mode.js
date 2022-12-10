import React from "react";
import {Descriptions} from "antd";

const StaticMode = ({userInfo}) => (
  <Descriptions column={1}>
    {userInfo.map(({label, value}) => (
      <Descriptions.Item key={label} label={label}>
        {value}
      </Descriptions.Item>
    ))}
  </Descriptions>
);

export default StaticMode;
