import React from "react";
import {shallowEqual, useSelector} from "react-redux";
import {selectOfferData} from "../../selector/home";
import {List, Button} from "antd";

const OfferModal = ({acceptOffer}) => {
  const offerData = useSelector(selectOfferData, shallowEqual);

  return offerData ? (
    <List
      itemLayout="horizontal"
      dataSource={offerData}
      renderItem={({legalName, creditScore, email, id}) => (
        <List.Item
          actions={[
            <Button type="primary" onClick={() => acceptOffer(id)}>
              Accept
            </Button>,
          ]}
        >
          <List.Item.Meta title={legalName} description={email} />
          <div>Credit Score: {creditScore}</div>
        </List.Item>
      )}
    />
  ) : (
    ""
  );
};

export default OfferModal;
