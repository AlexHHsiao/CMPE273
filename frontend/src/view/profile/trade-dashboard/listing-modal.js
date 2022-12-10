import React from "react";
import {dateFormat, validateMessages} from "../../../utils/constants/form";
import {
  Col,
  Form,
  Input,
  Row,
  Select,
  InputNumber,
  Upload,
  Button,
  DatePicker,
} from "antd";
import {UploadOutlined} from "@ant-design/icons";
import {useDispatch} from "react-redux";
import moment from "moment";

const ListingModal = ({form, homeData}) => {
  const {Option} = Select;
  const dispatch = useDispatch();

  if (homeData) {
    const currentHomeData = {...homeData};
    currentHomeData.openHour = moment(currentHomeData.openHour, dateFormat);
    currentHomeData.imageUrlList = currentHomeData.imageUrlList.map((val, index) => {
      return {
        uid: index,
        status: "done",
        thumbUrl: val,
        response: [val],
      };
    });
    form.setFieldsValue(currentHomeData);
  }

  const normFile = (event) => {
    if (Array.isArray(event)) {
      return event;
    }
    return event && event.fileList;
  };

  const onPreview = (image) => {
    if (image.error) {
      dispatch({
        type: "message/errorMessage",
        payload: "Cannot preview failed image",
      });
    } else {
      window.open(image.response[0]);
    }
  };

  const onChange = (info) => {
    if (info.file.status === "error") {
      dispatch({
        type: "message/errorMessage",
        payload: `${info.file.response.status} : ${info.file.response.message}`,
      });
    }
  };

  return (
    <Form form={form} layout="vertical" name="search" validateMessages={validateMessages}>
      <Row gutter={[5, 5]}>
        <Col span={12}>
          <Form.Item
            name="offerType"
            label="Offer Type"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Select placeholder="Select a offer type">
              <Option value="SELL">Sell</Option>
              <Option value="RENT_OUT">Rent</Option>
            </Select>
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            name="homeType"
            label="Home Type"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Select placeholder="Select a home type">
              <Option value="APARTMENT">Apartment</Option>
              <Option value="TOWN_HOUSE">Town House</Option>
              <Option value="SINGLE_HOUSE">Single House</Option>
            </Select>
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Bedroom"
            name="numOfBedroom"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <InputNumber />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Bathroom"
            name="numOfBathroom"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <InputNumber />
          </Form.Item>
        </Col>

        <Col span={12}>
          <Form.Item
            label="Price"
            name="price"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <InputNumber />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Square Feet"
            name="sqft"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <InputNumber />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Street Number"
            name="streetNumber"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Street1"
            name="street1"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item label="Street2" name="street2">
            <Input />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="City"
            name="city"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="State"
            name="state"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Zip"
            name="zip"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input />
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            name="hasParking"
            label="Parking"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Select placeholder="Select a parking option">
              <Option value={true}>Yes</Option>
              <Option value={false}>No</Option>
            </Select>
          </Form.Item>
        </Col>
        <Col span={12}>
          <Form.Item
            label="Open Hour"
            name="openHour"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <DatePicker />
          </Form.Item>
        </Col>
        <Col span={24}>
          <Form.Item
            name="description"
            label="Description"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Input.TextArea />
          </Form.Item>
        </Col>
        <Col span={24}>
          <Form.Item
            name="imageUrlList"
            label="Image"
            valuePropName="fileList"
            getValueFromEvent={normFile}
            extra="support: .png .jpg .jpeg"
            rules={[
              {
                required: true,
              },
            ]}
          >
            <Upload
              name="file"
              onChange={onChange}
              listType="picture"
              multiple={true}
              action="http://cmpe-load-balancer-1072138935.us-west-2.elb.amazonaws.com/v1/storage/upload"
              accept=".png,.jpg,.jpeg"
              onPreview={onPreview}
            >
              <Button icon={<UploadOutlined />}>Click to upload</Button>
            </Upload>
          </Form.Item>
        </Col>
      </Row>
    </Form>
  );
};

export default ListingModal;
