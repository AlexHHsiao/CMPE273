import React, {useState} from "react";
import {Col, Form, Menu, Row} from "antd";
import styled from "styled-components";
import {
  LogoutOutlined,
  SettingOutlined,
  SearchOutlined,
  DashboardOutlined,
  IdcardOutlined,
  HeartOutlined,
} from "@ant-design/icons";
import {withRouter, useLocation} from "react-router-dom";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectUserData} from "../../selector/user";
import {adminRole} from "../../../utils/constants/API";
import SearchModal from "../../dashboard/search-modal";

import CustomModal from "../modal";

const StyledMenu = styled.div`
  .ant-menu {
    border-bottom: 0;
    text-align: right;
  }
`;

const StyledTitle = styled.div`
  font-size: 30px;
  color: #096dd9;
  font-weight: bold;
  cursor: pointer;
`;

const HeaderSection = ({history}) => {
  const {pathname} = useLocation();
  const {SubMenu} = Menu;
  const [form] = Form.useForm();
  const dispatch = useDispatch();
  const userData = useSelector(selectUserData, shallowEqual);
  const [modalVisible, setModalVisible] = useState(false);

  const closeSearchModal = () => {
    setModalVisible(false);
  };

  const submitSearchModal = () => {
    form.validateFields().then((values) => {
      setModalVisible(false);
      dispatch({
        type: "home/getAllHome",
        payload: values,
      });
    });
  };

  const menuHandler = ({key}) => {
    switch (key) {
      case "admin": {
        history.push("/admin");
        break;
      }
      case "search": {
        setModalVisible(true);
        form.resetFields();
        break;
      }
      case "profile": {
        history.push("/profile");
        break;
      }
      case "favorite": {
        history.push("/favorite");
        break;
      }
      case "logout": {
        dispatch({
          type: "user/logout",
        });
        break;
      }
      default: {
      }
    }
  };

  const home = () => {
    history.push("/");
  };

  return (
    <>
      <CustomModal
        visible={modalVisible}
        onCancel={closeSearchModal}
        onOk={submitSearchModal}
        title="Search"
        form={form}
      >
        <SearchModal />
      </CustomModal>
      <Row>
        <Col xs={4} md={8}></Col>
        <Col xs={16} md={8} align="middle">
          <StyledTitle onClick={home}>Home Finder</StyledTitle>
        </Col>

        <Col xs={4} md={8}>
          {userData && (
            <StyledMenu>
              <Menu mode="horizontal" onClick={menuHandler}>
                {pathname === "/" && (
                  <Menu.Item key="search" icon={<SearchOutlined />}>
                    Search
                  </Menu.Item>
                )}
                <SubMenu
                  key="SubMenu"
                  icon={<SettingOutlined />}
                  title={userData.username}
                >
                  {userData.role === adminRole && (
                    <Menu.Item key="admin" icon={<IdcardOutlined />}>
                      Administration
                    </Menu.Item>
                  )}
                  <Menu.Item key="profile" icon={<DashboardOutlined />}>
                    Profile
                  </Menu.Item>
                  <Menu.Item key="favorite" icon={<HeartOutlined />}>
                    Favorite
                  </Menu.Item>
                  <Menu.Item key="logout" icon={<LogoutOutlined />}>
                    Logout
                  </Menu.Item>
                </SubMenu>
              </Menu>
            </StyledMenu>
          )}
        </Col>
      </Row>
    </>
  );
};

export default withRouter(HeaderSection);
