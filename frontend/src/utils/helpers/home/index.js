import moment from "moment";

export const mapURLHandler = (address) =>
  `https://www.google.com/maps?q=${address}&output=embed`;

export const addressHandler = ({streetNumber, street1, street2, city, state, zip}) =>
  `${streetNumber} ${street1}${street2 ? street2 : ""} ${city} ${state} ${zip}`;

export const homeSpecHandler = ({numOfBathroom, numOfBedroom, sqft}) =>
  `${numOfBedroom}bd | ${numOfBathroom}ba | ${sqft} sqft`;

export const dateHandler = (date) => {
  const end = moment(date);
  const start = moment();
  const diff = start.diff(end, "days");
  let result = "Posted ";

  if (diff === 0) {
    result += "today";
  } else if (diff < 7) {
    result += diff + " days ago";
  } else {
    result += date;
  }

  return result;
};

export const imageGridHandler = (imageList) => {
  return imageList.map((val) => {
    return {
      src: val,
      width: 1,
      height: 1,
    };
  });
};
