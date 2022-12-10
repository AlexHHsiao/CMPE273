import React, {useState, useEffect} from "react";
import {Card} from "antd";
import {cardTab} from "../../utils/constants/admin-dashboard";
import styled from "styled-components";
import UserList from "./user-list";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectPendingUserData, selectCurrentUserData} from "../selector/admin";

const StyledContainer = styled.div`
  padding: 40px 30px;
`;

const AdminDashboard = () => {
  const dispatch = useDispatch();
  const [selection, setSelection] = useState("pending");
  const pendingUserData = useSelector(selectPendingUserData, shallowEqual);
  const currentUserData = useSelector(selectCurrentUserData, shallowEqual);

  const contentList = {
    pending: <UserList userData={pendingUserData} action={selection} />,
    current: <UserList userData={currentUserData} action={selection} />,
  };

  const selectionHandler = (key) => {
    setSelection(key);
  };

  useEffect(() => {
    if (!pendingUserData && !currentUserData) {
      dispatch({
        type: "admin/getAllUser",
      });
    }
  }, []);

  return (
    <StyledContainer>
      <Card
        title="User Management"
        tabList={cardTab}
        activeTabKey={selection}
        onTabChange={(key) => selectionHandler(key)}
      >
        {contentList[selection]}
      </Card>
    </StyledContainer>
  );
};

export default AdminDashboard;
