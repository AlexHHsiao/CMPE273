import React from "react";
import {mapURLHandler} from "../../utils/helpers/home";

const MapModal = ({address}) => (
  <iframe
    title="Home Map"
    width="100%"
    height="500px"
    src={mapURLHandler(address)}
  ></iframe>
);
export default MapModal;
