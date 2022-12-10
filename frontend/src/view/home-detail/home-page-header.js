import React, {useState} from "react";
import {Button, Descriptions, PageHeader, Statistic, Tag} from "antd";
import styled from "styled-components";
import {withRouter} from "react-router-dom";
import CustomModal from "../components/modal";
import {
  mapModalTitle,
  homeTypePair,
  offerTypePair,
} from "../../utils/constants/home-detail";
import MapModal from "./map-modal";
import {addressHandler, homeSpecHandler} from "../../utils/helpers/home";
import {useDispatch} from "react-redux";

const StyledContainer = styled.div`
  display: flex;

  @media only screen and (max-width: 767.99px) {
    display: block;
  }
`;

const StyledExtraContent = styled.div`
  display: flex;
  width: max-content;
  justify-content: flex-end;
`;

const HomePageHeader = ({history, homeData}) => {
  const dispatch = useDispatch();
  const {
    price,
    available,
    offerType,
    homeType,
    hasParking,
    createdDate,
    openHour,
    homeId,
    hasApplied,
    owner: {username},
  } = homeData;

  const [modalVisible, setModalVisible] = useState(false);

  const sendOfferHandler = () => {
    dispatch({
      type: "home/setOffer",
      payload: homeId,
    });
  };

  const closeApplicationModal = () => {
    setModalVisible(false);
  };

  const openMapModal = () => {
    setModalVisible(true);
  };

  const goBack = () => {
    history.goBack();
  };

  return (
    <>
      <CustomModal
        visible={modalVisible}
        onCancel={closeApplicationModal}
        title={mapModalTitle}
        address={addressHandler(homeData)}
      >
        <MapModal />
      </CustomModal>
      <PageHeader
        onBack={goBack}
        tags={[
          <Tag key="available" color="blue">
            {available ? "Available" : "Not Available"}
          </Tag>,
          <Tag key="parking" color="green">
            {hasParking ? "Has Parking" : "No Parking"}
          </Tag>,
        ]}
        title={homeTypePair[homeType]}
        subTitle={offerTypePair[offerType]}
        extra={
          <Button
            disabled={!available || hasApplied}
            key="apply"
            onClick={sendOfferHandler}
            type="primary"
          >
            Submit Application
          </Button>
        }
      >
        <StyledContainer>
          <div>
            <Descriptions size="small" column={2}>
              <Descriptions.Item label="Owner">{username}</Descriptions.Item>
              <Descriptions.Item label="Address">
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                <a onClick={openMapModal}>{addressHandler(homeData)}</a>
              </Descriptions.Item>
              <Descriptions.Item label="Posted">{createdDate}</Descriptions.Item>
              <Descriptions.Item label="Specification">
                {homeSpecHandler(homeData)}
              </Descriptions.Item>
              <Descriptions.Item label="Open Hour">{openHour}</Descriptions.Item>
            </Descriptions>
          </div>
          <div>
            <StyledExtraContent>
              <Statistic
                title="Price"
                prefix="$"
                value={price}
                suffix={offerType === "SELL" ? "" : "/ month"}
              />
            </StyledExtraContent>
          </div>
        </StyledContainer>
      </PageHeader>
    </>
  );
};

export default withRouter(HomePageHeader);
