import React, {useEffect, useState} from "react";
import {Button, Card, Row, Col, Form, Empty} from "antd";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectUserHomeData} from "../../selector/home";
import OwnHomeCard from "./own-home-card";
import CustomModal from "../../components/modal";
import ListingModal from "./listing-modal";
import {dateFormat} from "../../../utils/constants/form";
import {listingModal, offerModal} from "../../../utils/constants/profile";
import OfferModal from "./offer-modal";

const TradeDashboard = () => {
  const dispatch = useDispatch();
  const [form] = Form.useForm();
  const userHomeData = useSelector(selectUserHomeData, shallowEqual);
  const [modalVisible, setModalVisible] = useState(false);
  const [modalType, setModalType] = useState(listingModal);
  const [homeId, setHomeId] = useState("");
  const [homeData, setHomeData] = useState(null);

  const closeListingModal = () => {
    setModalVisible(false);
  };

  const openListingModal = () => {
    setHomeData(null);
    setModalType(listingModal);
    setModalVisible(true);
    form.resetFields();
  };

  const submitListingModal = () => {
    form.validateFields().then((values) => {
      values.openHour = values.openHour.format(dateFormat);
      values.imageUrlList = values.imageUrlList.reduce((filtered, option) => {
        if (!option.error) {
          filtered.push(option.response[0]);
        }
        return filtered;
      }, []);

      if (values.imageUrlList.length > 0) {
        setModalVisible(false);

        if (homeData) {
          dispatch({
            type: "home/updateUserHome",
            payload: {id: homeData.homeId, body: values},
          });
        } else {
          dispatch({
            type: "home/setUserHome",
            payload: values,
          });
        }
      } else {
        dispatch({
          type: "message/warningMessage",
          payload: "Please have at least one uploaded image",
        });
      }
    });
  };

  const acceptOffer = (userId) => {
    setModalVisible(false);
    dispatch({
      type: "home/acceptUserOffer",
      payload: {userId, homeId},
    });
  };

  const offerHandler = (id) => {
    setModalType(offerModal);
    setModalVisible(true);
    setHomeId(id);
    dispatch({
      type: "home/getOffer",
      payload: id,
    });
  };

  const editHandler = (editHomeData) => {
    openListingModal();
    setHomeData(editHomeData);
  };

  useEffect(() => {
    if (!userHomeData) {
      dispatch({
        type: "home/getUserHome",
      });
    }
  }, []);

  return (
    <>
      <CustomModal
        visible={modalVisible}
        onCancel={closeListingModal}
        onOk={modalType === listingModal ? submitListingModal : undefined}
        title={modalType === listingModal ? "Listing" : "Offers"}
        form={form}
        acceptOffer={acceptOffer}
        homeData={homeData}
      >
        {modalType === listingModal ? <ListingModal /> : <OfferModal />}
      </CustomModal>
      <Card
        title="Listing"
        extra={
          <Button type="primary" onClick={openListingModal}>
            New Listing
          </Button>
        }
      >
        {userHomeData && userHomeData.length > 0 ? (
          <Row gutter={[20, 20]} style={{width: "100%", margin: "0"}}>
            {userHomeData.map((val) => (
              <Col xs={24} lg={12} xl={8} key={val.homeId}>
                <OwnHomeCard
                  homeData={val}
                  offerHandler={offerHandler}
                  editHandler={editHandler}
                />
              </Col>
            ))}
          </Row>
        ) : (
          <Empty style={{paddingTop: "40px"}} />
        )}
      </Card>
    </>
  );
};
export default TradeDashboard;
