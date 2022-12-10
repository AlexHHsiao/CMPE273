import React from "react";
import {Typography} from "antd";

const HomeDescription = ({homeData: {description}}) => {
  const {Title, Paragraph} = Typography;

  return (
    <Typography>
      <Title level={3}>Overview</Title>
      <Paragraph>{description}</Paragraph>
    </Typography>
  );
};

export default HomeDescription;
