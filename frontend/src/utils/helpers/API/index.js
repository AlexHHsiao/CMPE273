import {getAuthToken} from "../session-storage";

export const fetchHelper = (url, method, body) =>
  fetch(url, {
    method,
    headers: {
      "Content-Type": "application/json",
      Authorization: getAuthToken(),
    },
    ...(body ? {body: JSON.stringify(body)} : {}),
  }).then((response) => {
    if (response.ok) {
      if (method !== "DELETE") {
        return response.json();
      }
    } else {
      return response
        .text()
        .then((text) => throw {...JSON.parse(text), code: response.status});
    }
  });
