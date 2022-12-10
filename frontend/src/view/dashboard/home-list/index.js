import React, {useState, useEffect} from "react";
import {Pagination} from "antd";
import styled from "styled-components";
import {Row, Col} from "antd";
import HomeCard from "./home-card";
import {Empty} from "antd";

const StyledPagination = styled.div`
  height: 50px;
`;

const StyledContainer = styled.div`
  min-height: calc(100vh - 64px);
  display: flex;
  flex-direction: column;
`;

const StyledList = styled.div`
  flex: auto;
`;

const HomeList = ({homeData}) => {
  const [currentPage, setCurrentPage] = useState(homeData?.slice(0, 12) || []);

  const paginationHandler = (page) => {
    setCurrentPage(homeData.slice((page - 1) * 12, page * 12));
  };

  useEffect(() => {
    setCurrentPage(homeData?.slice(0, 12) || []);
  }, [homeData]);

  return (
    <StyledContainer>
      {homeData && homeData.length > 0 ? (
        <>
          <StyledList>
            <Row gutter={[40, 20]} style={{width: "100%", margin: "0"}}>
              {currentPage.map((val) => (
                <Col key={val.homeId} xs={24} md={12} xl={8}>
                  <HomeCard homeData={val} />
                </Col>
              ))}
            </Row>
          </StyledList>
          <StyledPagination>
            <Row justify="center" align="middle" style={{height: "100%", width: "100%"}}>
              <Col>
                <Pagination
                  onChange={paginationHandler}
                  defaultCurrent={1}
                  total={homeData?.length || 0}
                  defaultPageSize={12}
                  responsive={true}
                  showSizeChanger={false}
                />
              </Col>
            </Row>
          </StyledPagination>
        </>
      ) : (
        <Empty style={{paddingTop: "40px"}} />
      )}
    </StyledContainer>
  );
};

export default HomeList;
