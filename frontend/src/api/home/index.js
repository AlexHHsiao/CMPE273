import {baseUrl} from "../../utils/constants/API";
import {fetchHelper} from "../../utils/helpers/API";

export const getAllHome = (query, {userId, negation}) => {
  let queryOption = `?ownerId=${negation ? "-" : ""}${userId}`;
  if (query) {
    queryOption += "&";
    for (let key in query) {
      if (query[key]) {
        queryOption += `${key}=${query[key]}&`;
      }
    }
    queryOption = queryOption.substr(0, queryOption.length - 1);
  }

  return fetchHelper(`${baseUrl}homes${queryOption}`, "GET");
};

export const getAllFav = () => fetchHelper(`${baseUrl}fav`, "GET");

export const setFavHome = (id) => fetchHelper(`${baseUrl}fav/${id}`, "POST", {});

export const removeFavHome = (id) => fetchHelper(`${baseUrl}fav/${id}`, "DELETE");

export const getOffer = (id) => fetchHelper(`${baseUrl}homes/${id}/offers`, "GET");

export const setOffer = (id) => fetchHelper(`${baseUrl}homes/${id}/offer`, "POST", {});

export const setUserHome = (body) => fetchHelper(`${baseUrl}homes`, "POST", body);

export const updateUserHome = (id, body) =>
  fetchHelper(`${baseUrl}homes/${id}`, "PATCH", body);

export const removeUserHome = (id) => fetchHelper(`${baseUrl}homes/${id}`, "DELETE");

export const acceptUserOffer = (homeId, userId) =>
  fetchHelper(`${baseUrl}/homes/${homeId}/offers/${userId}/accept`, "GET");

export const getFavSearch = () => fetchHelper(`${baseUrl}search`, "GET");

export const setFavSearch = (body) => fetchHelper(`${baseUrl}search`, "POST", body);
