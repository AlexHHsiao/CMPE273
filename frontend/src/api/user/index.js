import {baseUrl} from "../../utils/constants/API";
import {fetchHelper} from "../../utils/helpers/API";
import {AUTHORIZATION} from "../../utils/constants/session-storage";
import {setAuthToken} from "../../utils/helpers/session-storage";

export const login = (body) =>
  fetch(`${baseUrl}login`, {
    method: "POST",
    body: JSON.stringify(body),
    headers: {
      "Content-Type": "application/json",
    },
  }).then((response) => {
    if (response.ok) {
      setAuthToken(response.headers.get(AUTHORIZATION));
      return response.json();
    } else {
      return response
        .text()
        .then((text) => throw {...JSON.parse(text), code: response.status || "500"});
    }
  });

export const register = (body) => fetchHelper(`${baseUrl}users`, "POST", body);

export const logout = () => fetchHelper(`${baseUrl}logout`, "DELETE");

export const update = (id, body) => fetchHelper(`${baseUrl}users/${id}`, "PATCH", body);
