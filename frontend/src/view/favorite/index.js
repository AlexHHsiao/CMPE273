import React, {useEffect} from "react";
import HomeList from "../dashboard/home-list";
import {shallowEqual, useDispatch, useSelector} from "react-redux";
import {selectAllFavData} from "../selector/home";

const Favorite = () => {
  const dispatch = useDispatch();
  const allFavData = useSelector(selectAllFavData, shallowEqual);

  useEffect(() => {
    if (!allFavData) {
      dispatch({
        type: "home/getAllFav",
      });
    }
  }, []);

  return <HomeList homeData={allFavData} />;
};

export default Favorite;
