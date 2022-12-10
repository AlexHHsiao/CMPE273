import React, {useEffect} from "react";
import HomeList from "./home-list";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectAllHomeData} from "../selector/home";

const Dashboard = () => {
  const dispatch = useDispatch();
  const allHomeData = useSelector(selectAllHomeData, shallowEqual);

  useEffect(() => {
    if (!allHomeData) {
      dispatch({
        type: "home/getAllHome",
      });

      dispatch({
        type: "home/getFavSearch",
      });
    }
  }, []);

  return <HomeList homeData={allHomeData} />;
};

export default Dashboard;
