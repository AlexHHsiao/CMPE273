import React from "react";
import {Card, Typography, Statistic, Popconfirm, Popover} from "antd";
import {
  EditOutlined,
  TeamOutlined,
  HomeOutlined,
  DeleteOutlined,
} from "@ant-design/icons";
import {homeTypePair, offerTypePair} from "../../../utils/constants/home-detail";
import {addressHandler, homeSpecHandler} from "../../../utils/helpers/home";
import {removeHomeConfirmMessage} from "../../../utils/constants/profile";
import {useDispatch} from "react-redux";

const OwnHomeCard = ({homeData, editHandler, offerHandler}) => {
  const dispatch = useDispatch();
  const {Paragraph, Title} = Typography;
  const {
    offerType,
    homeType,
    price,
    homeId,
    available,
    imageUrlList: [thumb],
  } = homeData;

  const deleteHandler = () => {
    dispatch({
      type: "home/removeUserHome",
      payload: homeId,
    });
  };

  return (
    <Card
      cover={<img alt="thumb" src={thumb} />}
      actions={[
        ...(available
          ? [
              <Popover content="Offers">
                <TeamOutlined onClick={() => offerHandler(homeId)} />
              </Popover>,
              <Popover content="Edit">
                <EditOutlined onClick={() => editHandler(homeData)} />
              </Popover>,
            ]
          : []),
        <Popconfirm
          title={removeHomeConfirmMessage}
          onConfirm={deleteHandler}
          okText="Yes"
          cancelText="No"
        >
          <DeleteOutlined />
        </Popconfirm>,
      ]}
    >
      <Statistic
        prefix="$"
        value={price}
        suffix={offerType === "SELL" ? "" : "/ month"}
      />
      <Typography>
        <Title
          level={5}
        >{`${homeTypePair[homeType]} | ${offerTypePair[offerType]}`}</Title>
        <Paragraph>{homeSpecHandler(homeData)}</Paragraph>
        <Paragraph>
          <HomeOutlined style={{paddingRight: "5px"}} />
          {addressHandler(homeData)}
        </Paragraph>
      </Typography>
    </Card>
  );
};

export default OwnHomeCard;
