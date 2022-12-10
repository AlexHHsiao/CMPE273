import React from "react";
import styled from "styled-components";
import {Typography, Statistic} from "antd";
import {HomeOutlined, HeartOutlined, HeartFilled} from "@ant-design/icons";
import {withRouter} from "react-router-dom";
import {addressHandler, homeSpecHandler} from "../../../utils/helpers/home";
import {useDispatch} from "react-redux";
import {homeTypePair, offerTypePair} from "../../../utils/constants/home-detail";
import {dateHandler} from "../../../utils/helpers/home";

const StyledContainer = styled.div`
  border-bottom: 2px solid #1890ff;
  height: 230px;
  padding-bottom: 5px;
  position: relative;
`;

const StyledContent = styled.div`
  display: flex;
  width: 100%;
  height: 100%;
  cursor: pointer;
  opacity: ${(props) => (props.available ? "1" : "0.6")};
`;

const StyledImage = styled.div`
  width: 45%;
  padding: 5px;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
`;

const StyledDescription = styled.div`
  width: 55%;
  padding: 15px 5px;
`;

const StyledHeader = styled.div`
  width: 100%;
  display: flex;
`;

const HomeCard = ({history, homeData}) => {
  const dispatch = useDispatch();
  const {Paragraph} = Typography;
  const {
    price,
    available,
    isFav,
    homeId,
    offerType,
    homeType,
    createdDate,
    imageUrlList: [thumb] = [],
  } = homeData;

  const homeCardHandler = () => {
    dispatch({
      type: "home/getCurrentHomeSuccess",
      payload: homeData,
    });
    history.push(`/home`);
  };

  const favHandler = (event) => {
    if (isFav) {
      dispatch({
        type: "home/removeFavHome",
        payload: homeId,
      });
    } else {
      dispatch({
        type: "home/setFavHome",
        payload: homeData,
      });
    }
    event.stopPropagation();
  };

  return (
    <StyledContainer onClick={homeCardHandler}>
      <StyledContent available={available}>
        <StyledImage>
          <img src={thumb} alt="thumb" />
        </StyledImage>
        <StyledDescription>
          <StyledHeader>
            <Statistic
              prefix="$"
              value={price}
              suffix={offerType === "SELL" ? "" : "/ month"}
            />
            {isFav ? (
              <HeartFilled
                style={{marginLeft: "auto", color: "#1890ff"}}
                onClick={favHandler}
              />
            ) : (
              <HeartOutlined
                style={{marginLeft: "auto", color: "#1890ff"}}
                onClick={favHandler}
              />
            )}
          </StyledHeader>
          <Typography>
            <Paragraph>{homeSpecHandler(homeData)}</Paragraph>
            <Paragraph>{`${homeTypePair[homeType]} | ${offerTypePair[offerType]}`}</Paragraph>
            <Paragraph>
              <HomeOutlined style={{paddingRight: "5px"}} />
              {addressHandler(homeData)}
            </Paragraph>
            <Paragraph>{dateHandler(createdDate)}</Paragraph>
          </Typography>
        </StyledDescription>
      </StyledContent>
    </StyledContainer>
  );
};

export default withRouter(HomeCard);
