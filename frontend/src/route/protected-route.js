import React from "react";
import {Route, Redirect} from "react-router-dom";
import {selectUserData} from "../view/selector/user";
import {useSelector, shallowEqual} from "react-redux";
import {adminRole} from "../utils/constants/API";

const ProtectedRoute = ({component: Component, path, ...rest}) => {
  const userData = useSelector(selectUserData, shallowEqual);

  return (
    <Route
      {...rest}
      render={(props) => {
        if (!userData) {
          return (
            <Redirect
              to={{
                pathname: "/login",
                state: {
                  from: props.location,
                },
              }}
            />
          );
        } else if (path === "/admin" && userData.role !== adminRole) {
          return (
            <Redirect
              to={{
                pathname: "*",
                state: {
                  from: props.location,
                },
              }}
            />
          );
        } else {
          return <Component {...props} />;
        }
      }}
    />
  );
};

export default ProtectedRoute;
