import React from "react";
import {validateMessages} from "../../utils/constants/form";
import {Col, Form, Input, Row, Select, InputNumber, Button} from "antd";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectFavSearchData} from "../selector/home";

const SearchModal = ({form}) => {
  const dispatch = useDispatch();
  const {Option} = Select;
  const favSearchData = useSelector(selectFavSearchData, shallowEqual);

  const favSearchChangeHandler = (value) => {
    form.resetFields();
    form.setFieldsValue(favSearchData[value]);
  };

  const setFavSearch = ({searchName}) => {
    form.validateFields().then((values) => {
      const payload = {name: searchName};

      for (let key in values) {
        if (values[key]) {
          payload[key] = values[key];
        }
      }

      dispatch({
        type: "home/setFavSearch",
        payload,
      });
    });
  };

  return (
    <>
      <Form
        layout="vertical"
        name="favSearch"
        validateMessages={validateMessages}
        onFinish={setFavSearch}
      >
        <Row gutter={[5, 5]}>
          <Col span={12}>
            <Form.Item name="savedSearch" label="Saved Search">
              <Select
                placeholder="Select a saved search"
                onChange={favSearchChangeHandler}
              >
                {favSearchData.map(({name}, index) => (
                  <Option key={index + name} value={index}>
                    {name}
                  </Option>
                ))}
              </Select>
            </Form.Item>
          </Col>

          <Col span={12}>
            <Form.Item
              name="searchName"
              label="Search Name"
              rules={[
                {
                  required: true,
                },
              ]}
            >
              <Input id="searchInput" />
            </Form.Item>
          </Col>

          <Form.Item>
            <Button type="primary" htmlType="submit">
              Save Search
            </Button>
          </Form.Item>
        </Row>
      </Form>

      <Form
        form={form}
        layout="vertical"
        name="search"
        validateMessages={validateMessages}
      >
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
            <Form.Item name="homeType" label="Home Type">
              <Select placeholder="Select a home type">
                <Option value="APARTMENT">Apartment</Option>
                <Option value="TOWN_HOUSE">Town House</Option>
                <Option value="SINGLE_HOUSE">Single House</Option>
              </Select>
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Bedroom" name="numOfBedroom">
              <InputNumber />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Bathroom" name="numOfBathroom">
              <InputNumber />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Min Price" name="minPrice">
              <InputNumber />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Max Price" name="maxPrice">
              <InputNumber />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Street Number" name="streetNumber">
              <Input />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Street 1" name="street1">
              <Input />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Street 2" name="street2">
              <Input />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="City" name="city">
              <Input />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Sate" name="state">
              <Input />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="Zip" name="zip">
              <Input />
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item name="hasParking" label="Parking">
              <Select placeholder="Select a parking option">
                <Option value={true}>Yes</Option>
                <Option value={false}>No</Option>
              </Select>
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </>
  );
};

export default SearchModal;
