import React, {useState, useEffect} from "react";
import {Button, Card} from "antd";
import {userCardTitle} from "../../../utils/constants/profile";
import StaticMode from "./static-mode";
import EditMode from "./edit-mode";
import {dateFormat} from "../../../utils/constants/form";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectUserData} from "../../selector/user";
import {formatData} from "../../../utils/helpers/profile";

const InfoDashboard = () => {
  const dispatch = useDispatch();
  const userData = useSelector(selectUserData, shallowEqual);
  const [editMode, setEditMode] = useState(false);
  const [userInfo, setUserInfo] = useState([]);

  const userInfoModeHandler = () => {
    setEditMode(!editMode);
  };

  const onFinish = (values) => {
    values.birthday = values.birthday.format(dateFormat);

    dispatch({
      type: "user/update",
      payload: values,
    });

    userInfoModeHandler();
  };

  useEffect(() => {
    setUserInfo(formatData(userData));
  }, [userData]);

  return (
    <Card
      title={userCardTitle}
      extra={
        <Button onClick={userInfoModeHandler} type="primary">
          {editMode ? "Cancel" : "Edit"}
        </Button>
      }
    >
      {editMode ? (
        <EditMode onFinish={onFinish} userInfo={userInfo} />
      ) : (
        <StaticMode userInfo={userInfo} />
      )}
    </Card>
  );
};

export default InfoDashboard;
