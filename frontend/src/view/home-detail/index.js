import React from "react";
import {Col, Row} from "antd";
import HomeDescription from "./home-description";
import HomeGallery from "./home-gallery";
import HomePageHeader from "./home-page-header";
import {shallowEqual, useSelector} from "react-redux";
import {selectCurrentHomeData} from "../selector/home";

const HomeDetail = () => {
  const currentHomeData = useSelector(selectCurrentHomeData, shallowEqual);

  return (
    <>
      <HomePageHeader homeData={currentHomeData} />
      <Row gutter={[40, 30]} style={{width: "100%", height: "100%", margin: "0"}}>
        <Col xs={24} md={12}>
          <HomeGallery imageData={currentHomeData.imageUrlList} />
        </Col>
        <Col xs={24} md={12}>
          <HomeDescription homeData={currentHomeData} />
        </Col>
      </Row>
    </>
  );
};

export default HomeDetail;
